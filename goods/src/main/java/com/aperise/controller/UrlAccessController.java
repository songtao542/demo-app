package com.aperise.controller;

import com.aperise.Result;
import com.aperise.bean.*;
import com.aperise.mapper.AclResourceMapper;
import com.aperise.mapper.UserMapper;
import com.aperise.model.Access;
import com.aperise.services.AccessService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UrlAccessController {
    protected static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    MutableAclService aclService;

    @Autowired
    AclResourceMapper aclResourceMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AccessService accessService;

    @Transactional
    @RequestMapping("/url/user/access")
    public Result configUrlAccess(String url, Integer userId, @RequestParam(value = "permission", defaultValue = "0") Integer permission) {
        if (userId == null) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "userId can't be empty!");
        }
        if (StringUtils.isEmpty(url)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "url can't be empty!");
        }

        logger.debug("/url/access userId=" + userId + "  url=" + url);

        AclResource resource = aclResourceMapper.selectByResource(url);

        if (resource == null) {
            return Result.ERROR(Result.Status.DATA_NOT_EXIST, "resource not exist!");
        }

        if (permission == 1) {
            ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
            MutableAcl acl = aclService.createAcl(oid);
            acl.insertAce(0, BasePermission.READ,
                    new PrincipalSid(String.valueOf(userId)), true);
            aclService.updateAcl(acl);
        } else {
            ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
            MutableAcl acl = aclService.createAcl(oid);
            List<AccessControlEntry> aces = acl.getEntries();
            if (aces != null) {
                PrincipalSid sid = new PrincipalSid(String.valueOf(userId));
                for (AccessControlEntry ace : aces) {
                    if (ace.getSid().equals(sid)) {
                        acl.deleteAce(acl.getEntries().indexOf(ace));
                    }
                }
            }
            aclService.updateAcl(acl);
        }
        return Result.OK("success");
    }


    @Transactional
    @Secured("ROLE_ADMIN")
    @RequestMapping("/url/access")
    public Result configUrlAccess(HttpServletResponse response, String url, String permission) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        logger.debug("/url/access permission=" + permission + "  url=" + url);
        if (StringUtils.isEmpty(permission)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "permission Json can't be empty!");
        }
        if (StringUtils.isEmpty(url)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "url can't be empty!");
        }

        AclResource resource = aclResourceMapper.selectByResource(url);

        if (resource == null) {
            return Result.ERROR(Result.Status.DATA_NOT_EXIST, "resource not exist!");
        }

        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Permission.class);
        ArrayList<Permission> permissions;
        try {
            permissions = mapper.readValue(permission, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("/url/access parse json error");
            return Result.ERROR(Result.Status.PARAMETER_ERROR, "parameter error!");
        }
        logger.debug("/url/access permissions=" + permissions);
        if (permissions != null) {
            for (Permission p : permissions) {
                if (p.granted) {
                    ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
                    MutableAcl acl = aclService.createAcl(oid);
                    acl.insertAce(0, BasePermission.READ,
                            new PrincipalSid(String.valueOf(p.userId)), true);
                    aclService.updateAcl(acl);
                } else {
                    boolean changed = false;
                    ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
                    MutableAcl acl = (MutableAcl) aclService.readAclById(oid);
                    List<AccessControlEntry> aces = acl.getEntries();
                    if (aces != null) {
                        PrincipalSid sid = new PrincipalSid(String.valueOf(p.userId));
                        for (AccessControlEntry ace : aces) {
                            if (ace.getSid().equals(sid)) {
                                changed = true;
                                acl.deleteAce(acl.getEntries().indexOf(ace));
                            }
                        }
                    }
                    if (changed) {
                        aclService.updateAcl(acl);
                    }
                }
            }
        }
        return Result.OK("success");
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/access/manage")
    public Result accessManage() {
        List<Access> accesses = accessService.getAccesses();
        return Result.OK(accesses);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    @RequestMapping("/resource/add")
    public Result addResource(String resource, String display) {
        if (StringUtils.isEmpty(resource)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "resource can't be empty!");
        }
        if (StringUtils.isEmpty(display)) {
            return Result.ERROR(Result.Status.PARAMETER_MISSING, "display can't be empty!");
        }

        AclResource res = aclResourceMapper.selectByResource(resource);

        if (res != null) {
            return Result.ERROR(Result.Status.DATA_ALREADY_EXIST, "resource already exist!");
        }

        res = new AclResource();
        res.setResource(resource);
        res.setDisplay(display);

        aclResourceMapper.insertSelective(res);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        logger.debug("res id == " + res.getId());

        ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, res.getId());
        MutableAcl acl = aclService.createAcl(oid);
        acl.setOwner(new GrantedAuthoritySid("ROLE_ADMIN"));
        acl.insertAce(0, BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_ADMIN"), true);
        aclService.updateAcl(acl);
        return Result.OK("success");
    }


    public static class Permission {
        private Long userId;
        private Boolean granted;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Boolean getGranted() {
            return granted;
        }

        public void setGranted(Boolean granted) {
            this.granted = granted;
        }
    }
}
