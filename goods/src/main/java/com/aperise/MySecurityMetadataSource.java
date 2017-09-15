package com.aperise;

import com.aperise.MyUserDetailsService.MyGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Component("filterInvocationSecurityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    protected static Logger logger = LoggerFactory.getLogger(MySecurityMetadataSource.class);

    @Autowired
    DataSource dataSource;

    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    private AclService aclService;
    private LookupStrategy lookupStrategy;
    private AclCache aclCache;
    private PermissionGrantingStrategy permissionGrantingStrategy;
    private AclAuthorizationStrategy aclAuthorizationStrategy;
    private AuditLogger auditLogger;


    public MySecurityMetadataSource() {
        loadMetadataSource();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        logger.debug("---------------------getAttributes---------------------object=" + object);
        FilterInvocation filterInvocation;
        ArrayList<ConfigAttribute> configAttributes = new ArrayList<>();



        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        logger.debug("---------------------getAllConfigAttributes---------------------");
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        logger.debug("---------------------supports---------------------clazz=" + clazz);
        return false;
    }


    private void loadMetadataSource() {
        if (requestMap == null) {
            requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
        }
       AclService aclService = getAclService();
       

    }


    public AclService getAclService() {
        if (aclService == null) {
            aclService = new JdbcMutableAclService(dataSource, getLookupStrategy(), getAclCache());
        }
        return aclService;
    }

    public LookupStrategy getLookupStrategy() {
        if (lookupStrategy == null) {
            lookupStrategy = new BasicLookupStrategy(dataSource, getAclCache(), getAclAuthorizationStrategy(), getAuditLogger());
        }
        return lookupStrategy;
    }

    public AclCache getAclCache() {
        if (aclCache == null) {
            aclCache = new SpringCacheBasedAclCache(new ConcurrentMapCache("acl_cache"), getPermissionGrantingStrategy(), getAclAuthorizationStrategy());
        }
        return aclCache;
    }

    public PermissionGrantingStrategy getPermissionGrantingStrategy() {
        if (permissionGrantingStrategy == null) {
            permissionGrantingStrategy = new DefaultPermissionGrantingStrategy(getAuditLogger());
        }
        return permissionGrantingStrategy;
    }

    public AclAuthorizationStrategy getAclAuthorizationStrategy() {
        if (aclAuthorizationStrategy == null) {
            aclAuthorizationStrategy = new AclAuthorizationStrategyImpl(new MyGrantedAuthority("admin"), new MyGrantedAuthority("admin"), new MyGrantedAuthority("admin"));
        }
        return aclAuthorizationStrategy;
    }

    public AuditLogger getAuditLogger() {
        if (auditLogger == null) {
            auditLogger = new ConsoleAuditLogger();
        }
        return auditLogger;
    }

}
