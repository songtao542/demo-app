package com.aperise.services;

import com.aperise.acl.AclResourceService;
import com.aperise.bean.AclResource;
import com.aperise.bean.AclResourceCriteria;
import com.aperise.bean.User;
import com.aperise.bean.UserCriteria;
import com.aperise.controller.ProductController;
import com.aperise.mapper.AclResourceMapper;
import com.aperise.mapper.UserMapper;
import com.aperise.model.Access;
import com.aperise.model.UserAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class AccessService {
    protected static Logger logger = LoggerFactory.getLogger(AccessService.class);

    @Autowired
    AclResourceMapper aclResourceMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AclService aclService;

    public List<Access> getAccesses() {
        AclResourceCriteria criteria = new AclResourceCriteria();
        List<AclResource> resources = aclResourceMapper.selectByCriteria(criteria);

        UserCriteria userCriteria = new UserCriteria();
        List<User> users = userMapper.selectByCriteria(userCriteria);

        List<Access> accesses = new ArrayList<>();
        List<Permission> permissions = new ArrayList<>();
        permissions.add(BasePermission.ADMINISTRATION);
        permissions.add(BasePermission.READ);

        List<Sid> sids = new ArrayList<>(1);
        for (User user : users) {
            sids.add(new PrincipalSid(String.valueOf(user.getId())));
        }

        for (AclResource resource : resources) {
            if (logger.isDebugEnabled())
                logger.debug("resource:" + resource.getDisplay());

            ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
            Acl acl = aclService.readAclById(oid, sids);

            Access access = new Access();
            access.setDisplay(resource.getDisplay());
            access.setResource(resource.getResource());
            access.setId(resource.getId());
            List<Access.Authority> authorities = new ArrayList<>();

            for (User user : users) {
                try {
                    boolean isGranted = acl.isGranted(permissions, Arrays.asList(new PrincipalSid(String.valueOf(user.getId()))), false);
                    if (logger.isDebugEnabled())
                        logger.debug("user:" + user.getName() + " isGranted:" + isGranted);
                    authorities.add(Access.Authority.from(user, isGranted));
                } catch (NotFoundException e) {
                    if (logger.isDebugEnabled())
                        logger.debug("user:" + user.getName() + " isGranted:false");
                    authorities.add(Access.Authority.from(user, false));
                }
            }
            if (logger.isDebugEnabled())
                logger.debug("access authorities:" + authorities);
            access.setAuthorities(authorities);
            accesses.add(access);
        }
        return accesses;
    }

    public List<UserAuthority> getUserAuthorities() {
        AclResourceCriteria criteria = new AclResourceCriteria();
        List<AclResource> resources = aclResourceMapper.selectByCriteria(criteria);

        UserCriteria userCriteria = new UserCriteria();
        List<User> users = userMapper.selectByCriteria(userCriteria);

        List<UserAuthority> authorities = new ArrayList<>();
        List<Permission> permissions = new ArrayList<>();
        permissions.add(BasePermission.ADMINISTRATION);
        permissions.add(BasePermission.READ);

        List<Sid> sids = new ArrayList<>(1);
        for (User user : users) {
            sids.add(new PrincipalSid(String.valueOf(user.getId())));
        }

        for (User user : users) {
            if (logger.isDebugEnabled())
                logger.debug("user name:" + user.getName());

            UserAuthority authority = new UserAuthority();
            authority.setId(user.getId());
            authority.setName(user.getName());
            authority.setNickname(user.getNickname());

            List<UserAuthority.Access> accesses = new ArrayList<>();

            for (AclResource resource : resources) {
                ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
                Acl acl = aclService.readAclById(oid, sids);
                try {
                    boolean isGranted = acl.isGranted(permissions, Arrays.asList(new PrincipalSid(String.valueOf(user.getId()))), false);
                    if (logger.isDebugEnabled())
                        logger.debug("resource:" + resource.getDisplay() + " isGranted:" + isGranted);
                    accesses.add(UserAuthority.Access.from(resource, isGranted));
                } catch (NotFoundException e) {
                    if (logger.isDebugEnabled())
                        logger.debug("resource:" + resource.getDisplay() + " isGranted:false");
                    accesses.add(UserAuthority.Access.from(resource, false));
                }
            }
            if (logger.isDebugEnabled())
                logger.debug("authority accesses:" + authorities);
            authority.setAccesses(accesses);
            authorities.add(authority);
        }
        return authorities;
    }

    public boolean checkGranted(AclResource resource) {
        if (logger.isDebugEnabled())
            logger.debug("checkGranted resource id:" + resource.getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Sid> sids = new ArrayList<>(authorities.size() + 1);
        sids.add(new PrincipalSid(authentication));
        for (GrantedAuthority authority : authorities) {
            sids.add(new GrantedAuthoritySid(authority));
        }

        Acl acl = aclService.readAclById(oid, sids);

        List<Permission> permissions = new ArrayList<>();
        permissions.add(BasePermission.ADMINISTRATION);
        permissions.add(BasePermission.READ);
        try {
            boolean isGranted = acl.isGranted(permissions, sids, false);
            if (logger.isDebugEnabled())
                logger.debug("resource:" + resource.getDisplay() + " isGranted:" + isGranted);
            return isGranted;
        } catch (NotFoundException e) {
        }
        return false;
    }
}
