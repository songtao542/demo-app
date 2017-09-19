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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
                logger.debug("resource:" + resource);

            ObjectIdentity oid = new ObjectIdentityImpl(AclResource.class, resource.getId());
            Acl acl = aclService.readAclById(oid, sids);

            Access access = new Access();
            access.setDisplay(resource.getDisplay());
            access.setResource(resource.getResource());
            access.setId(resource.getId());
            List<User> grantedUsers = new ArrayList<>();
            List<User> ungrantedUsers = new ArrayList<>();

            for (User user : users) {
                if (logger.isDebugEnabled())
                    logger.debug("user:" + user);
                try {
                    boolean isGranted = acl.isGranted(permissions, Arrays.asList(new PrincipalSid(String.valueOf(user.getId()))), false);
                    if (isGranted) {
                        grantedUsers.add(user);
                    } else {
                        ungrantedUsers.add(user);
                    }
                } catch (NotFoundException e) {
                    ungrantedUsers.add(user);
                }
            }
            access.setGrantedUsers(grantedUsers);
            access.setUngrantedUsers(ungrantedUsers);
            accesses.add(access);
        }
        return accesses;
    }
}
