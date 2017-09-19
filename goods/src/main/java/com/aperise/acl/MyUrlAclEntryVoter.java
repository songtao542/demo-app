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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUrlAclEntryVoter extends MyAbstractAclVoter {
    // ~ Static fields/initializers
    // =====================================================================================

    private static final Log logger = LogFactory.getLog(MyUrlAclEntryVoter.class);

    // ~ Instance fields
    // ================================================================================================
    private AclResourceService aclResourceService;

    // ~ Constructors
    // ===================================================================================================
    public MyUrlAclEntryVoter(AclResourceService aclResourceService, AclService aclService, String processConfigAttribute,
                              Permission[] requirePermission) {
        super(aclService, processConfigAttribute, requirePermission);
        this.aclResourceService = aclResourceService;
    }

    @Override
    protected Object getDomainObjectInstance(MethodInvocation invocation) {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return aclResourceService.readBySource(attrs.getRequest().getRequestURI());
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new AuthorizationServiceException("MethodInvocation: " + invocation
                + " can not get domain object(" + getProcessDomainObjectClass() + ")");
    }
}

