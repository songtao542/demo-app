package com.aperise;

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
