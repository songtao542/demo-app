package com.aperise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.PermissionGrantingStrategy;

import javax.sql.DataSource;

import com.aperise.MyUserDetailsService.MyGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class MySecurityConfiguration {

//    public static final Permission[] permissions = new Permission[]{
//            BasePermission.READ,
//            BasePermission.WRITE,
//            BasePermission.CREATE,
//            BasePermission.DELETE,
//            BasePermission.ADMINISTRATION
//    };
//
//    @Autowired
//    DataSource dataSource;
//
//    @Bean
//    public AclService getAclService(LookupStrategy lookupStrategy, AclCache aclCache) {
//        return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
//    }
//
//    @Bean
//    public LookupStrategy getLookupStrategy(AclCache aclCache, AclAuthorizationStrategy aclAuthorizationStrategy, AuditLogger auditLogger) {
//        return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
//    }
//
//    @Bean
//    public AclCache getAclCache(PermissionGrantingStrategy permissionGrantingStrategy, AclAuthorizationStrategy aclAuthorizationStrategy) {
//        return new SpringCacheBasedAclCache(new ConcurrentMapCache("acl_cache"), permissionGrantingStrategy, aclAuthorizationStrategy);
//    }
//
//    @Bean
//    public PermissionGrantingStrategy getPermissionGrantingStrategy(AuditLogger auditLogger) {
//        return new DefaultPermissionGrantingStrategy(auditLogger);
//    }
//
//    @Bean
//    public AclAuthorizationStrategy getAclAuthorizationStrategy() {
//        return new AclAuthorizationStrategyImpl(new MyGrantedAuthority("admin"), new MyGrantedAuthority("admin"), new MyGrantedAuthority("admin"));
//    }
//
//    @Bean
//    public AuditLogger getAuditLogger() {
//        return new ConsoleAuditLogger();
//    }
//
//    @Bean("aclUserDeleteVoter")
//    public AclEntryVoter getAclUserDeleteVoter(AclService aclService) {
//        return new AclEntryVoter(aclService, "ACL_USER_DELETE", new Permission[]{
//                BasePermission.DELETE,
//                BasePermission.ADMINISTRATION
//        });
//    }
//
//    @Bean
//    public List<AccessDecisionVoter<?>> getAccessDecisionVoters(AclService aclService,) {
//        List<AccessDecisionVoter<?>> voters = new ArrayList<>();
//        RoleVoter roleVoter = new RoleVoter();
//        voters.add(roleVoter);
//
//        AclEntryVoter aclEntryVoter = new AclEntryVoter(aclService, null, permissions);
//        voters.add(aclEntryVoter);
//        return voters;
//    }

}
