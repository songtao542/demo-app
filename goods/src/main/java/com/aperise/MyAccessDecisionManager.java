package com.aperise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.acls.domain.BasePermission;

//@Component("accessDecisionManager")
public class MyAccessDecisionManager extends AffirmativeBased {
    protected static Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    @Autowired
    protected MyAccessDecisionManager(List<AccessDecisionVoter<?>> decisionVoters) {
        super(decisionVoters);
    }


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        logger.debug("-----------------decide access-----------------");
        logger.debug("authentication object=" + object);
        logger.debug("authentication authentication=" + authentication);
        logger.debug("authentication Principal=" + authentication.getPrincipal());
        logger.debug("authentication Name=" + authentication.getName());
        logger.debug("authentication Authorities=" + authentication.getAuthorities());
        logger.debug("authentication Credentials=" + authentication.getCredentials());
        logger.debug("authentication Details=" + authentication.getDetails());
        logger.debug("-----------------------------------------------");
        logger.debug("authentication configAttributes=" + configAttributes);
        logger.debug("-----------------decide access-----------------");
        super.decide(authentication, object, configAttributes);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        logger.debug("-----------------supports attribute-----------------");
        logger.debug("authentication attribute=" + attribute.getAttribute());
        logger.debug("-----------------supports attribute-----------------");
        return super.supports(attribute);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        logger.debug("-----------------supports clazz-----------------");
        return super.supports(clazz);
    }


}
