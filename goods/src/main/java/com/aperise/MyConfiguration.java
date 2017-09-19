package com.aperise;

import com.aperise.acl.AclResourceService;
import com.aperise.acl.AclResourceServiceImpl;
import com.aperise.acl.MyAclEntryVoter;
import com.aperise.acl.MyUrlAclEntryVoter;
import com.aperise.bean.AclResource;
import com.aperise.bean.Product;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//@ImportResource(locations = {"application-context.xml", "security-config.xml"})
@EnableWebSecurity
@MapperScan({"com.aperise.mapper"})
public class MyConfiguration {

    protected static Logger logger = LoggerFactory.getLogger(MyConfiguration.class);

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8082");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/user/*", config);
        source.registerCorsConfiguration("/game/*", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public AclService getAclService(DataSource dataSource, LookupStrategy lookupStrategy, @Qualifier("aclCache") AclCache aclCache) {
        JdbcMutableAclService service = new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
        service.setSidIdentityQuery("select max(id) from acl_sid");
        service.setClassIdentityQuery("select max(id) from acl_class");
        return service;
    }

    @Bean
    public LookupStrategy getLookupStrategy(DataSource dataSource, @Qualifier("aclCache") AclCache aclCache, AclAuthorizationStrategy aclAuthorizationStrategy, AuditLogger auditLogger) {
        return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
    }

    @Bean("aclCache")
    public AclCache getAclCache(PermissionGrantingStrategy permissionGrantingStrategy, AclAuthorizationStrategy aclAuthorizationStrategy) {
        return new SpringCacheBasedAclCache(new ConcurrentMapCache("acl_cache"), permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    public PermissionGrantingStrategy getPermissionGrantingStrategy(AuditLogger auditLogger) {
        return new DefaultPermissionGrantingStrategy(auditLogger);
    }

    @Bean
    public AclAuthorizationStrategy getAclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public AuditLogger getAuditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean("aclProductDeleteVoter")
    public AccessDecisionVoter getAclProductDeleteVoter(AclService aclService) {
        MyAclEntryVoter voter = new MyAclEntryVoter(aclService, "ACL_DELETE_PRODUCT", new Permission[]{
                BasePermission.DELETE,
                BasePermission.ADMINISTRATION
        });
        voter.setProcessDomainObjectClass(Product.class);
        return voter;
    }

    @Bean("aclResourceCache")
    public Cache getAclResourceCache() {
        return new ConcurrentMapCache("acl_resource_cache");
    }

    @Bean("aclResourceService")
    public AclResourceService getAclResourceService(DataSource dataSource, @Qualifier("aclResourceCache") Cache cache) {
        return new AclResourceServiceImpl(dataSource, cache);
    }

    @Bean("aclResourceReadVoter")
    public AccessDecisionVoter getAclResourceReadVoter(AclResourceService aclResourceService, AclService aclService) {
        MyUrlAclEntryVoter voter = new MyUrlAclEntryVoter(aclResourceService, aclService, "ACL_READ_RESOURCE", new Permission[]{
                BasePermission.READ
        });
        voter.setProcessDomainObjectClass(AclResource.class);
        return voter;
    }

    @Bean("roleVote")
    public AccessDecisionVoter getRoleVoter() {
        return new RoleVoter();
    }


    //public List<AccessDecisionVoter<?>> getAccessDecisionVoters(@Value("#{roleVote}") AccessDecisionVoter roleVoter, @Value("#{aclProductDeleteVoter}") AccessDecisionVoter aclProductDeleteVoter) {
    @Bean("aclAccessDecisionVoters")
    public List<AccessDecisionVoter<?>> getAccessDecisionVoters(@Qualifier("roleVote") AccessDecisionVoter roleVoter,
                                                                @Qualifier("aclProductDeleteVoter") AccessDecisionVoter aclProductDeleteVoter,
                                                                @Qualifier("aclResourceReadVoter") AccessDecisionVoter aclResourceReadVoter) {
        List<AccessDecisionVoter<?>> voters = new ArrayList<>();
        voters.add(roleVoter);
        voters.add(aclProductDeleteVoter);
        voters.add(aclResourceReadVoter);
        return voters;
    }

    @Bean("aclAccessDecisionManager")
    public AccessDecisionManager getAccessDecisionManager(List<AccessDecisionVoter<?>> aclAccessDecisionVoters) {
        return new MyAccessDecisionManager(aclAccessDecisionVoters);
    }

}
