package com.aperise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("accessDecisionManager")
public class MyAccessDecisionManager implements AccessDecisionManager {
    protected static Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        logger.debug("-----------------decide access-----------------");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        logger.debug("-----------------supports attribute-----------------");
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        logger.debug("-----------------supports clazz-----------------");
        return true;
    }
}
