package com.aperise.controller;

import com.aperise.Result;
import com.aperise.bean.AclResource;
import com.aperise.bean.Product;
import com.aperise.mapper.AclResourceMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UrlAccessController {
    protected static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    MutableAclService aclService;

    @Autowired
    AclResourceMapper aclResourceMapper;

    @Transactional
    @RequestMapping("/url/access")
    public Result setUrlAccess(Integer userId, String url, @RequestParam(value = "permission", defaultValue = "0") Integer permission) {
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
}
