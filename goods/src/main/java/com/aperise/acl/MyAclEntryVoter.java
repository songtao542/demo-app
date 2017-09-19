package com.aperise.acl;

/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAclVoter;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MyAclEntryVoter extends MyAbstractAclVoter {
    // ~ Static fields/initializers
    // =====================================================================================

    private static final Log logger = LogFactory.getLog(MyAclEntryVoter.class);

    // ~ Constructors
    // ===================================================================================================
    public MyAclEntryVoter(AclService aclService, String processConfigAttribute, Permission[] requirePermission) {
        super(aclService, processConfigAttribute, requirePermission);
    }

    @Override
    protected Object getDomainObjectInstance(MethodInvocation invocation) {
        Object[] args;
        Class<?>[] params;

        params = invocation.getMethod().getParameterTypes();
        args = invocation.getArguments();

        for (int i = 0; i < params.length; i++) {
            if (getProcessDomainObjectClass().isAssignableFrom(params[i])) {
                return args[i];
            } else if (Number.class.isAssignableFrom(params[i])) {
                return newDomainObjectInstanceById(getProcessDomainObjectClass(), (Number) args[i]);
            }
        }
        throw new AuthorizationServiceException("MethodInvocation: " + invocation
                + " did not provide any argument of type: " + getProcessDomainObjectClass()
                + " or can not create domain object(" + getProcessDomainObjectClass() + ") by provided argument");
    }

    private Object newDomainObjectInstanceById(Class domain, Number id) {
        try {
            Object obj = domain.newInstance();
            Method setId = findMethod(domain, "setId", new Class[]{Integer.class});
            if (setId != null) {
                setId.invoke(obj, id.intValue());
            }

            setId = findMethod(domain, "setId", new Class[]{Long.class});
            if (setId != null) {
                setId.invoke(obj, id.longValue());
            }

            setId = findMethod(domain, "setId", new Class[]{Short.class});
            if (setId != null) {
                setId.invoke(obj, id.shortValue());
            }
            return obj;
        } catch (IllegalAccessException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Can not create instance of " + domain.getName());
            }
        } catch (InvocationTargetException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage());
            }
        } catch (InstantiationException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage());
            }
        }
        return null;
    }

    private Method findMethod(Class who, String methodName, Class[] argClasses) {
        try {
            return who.getMethod(methodName, argClasses);
        } catch (NoSuchMethodException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(who.getName() + "has no " + methodName + "() method");
            }
        }
        return null;
    }
}

