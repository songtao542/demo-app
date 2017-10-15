/*
 * Copyright 2002-2013 the original author or authors.
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
package org.springframework.security.oauth.examples.sparklr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth.examples.sparklr.ApeRiseTokenEnhancer;
import org.springframework.security.oauth.examples.sparklr.oauth.SparklrUserApprovalHandler;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private static final String SPARKLR_RESOURCE_ID = "sparklr";

    @Autowired
    @Qualifier("jdbcTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("myTokenEnhancer")
    TokenEnhancer tokenEnhancer;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Value("${tonr.redirect:http://localhost:8083/sparklr/redirect}")
    private String tonrRedirectUri;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // @formatter:off

        clients.inMemory().withClient("tonr")
                .resourceIds(SPARKLR_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .secret("secret")
                .and()
                .withClient("tonr-with-redirect")
                .resourceIds(SPARKLR_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .secret("secret")
                .redirectUris(tonrRedirectUri)
                .and()
                .withClient("my-client-with-registered-redirect")
                .resourceIds(SPARKLR_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "client_credentials")
                .authorities("ROLE_CLIENT")
                .scopes("read", "trust")
                .redirectUris("http://anywhere?key=value")
                .and()
                .withClient("my-trusted-client")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .accessTokenValiditySeconds(60)
                .and()
                .withClient("my-trusted-client-with-secret")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .secret("somesecret")
                .and()
                .withClient("my-less-trusted-client")
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write", "trust")
                .and()
                .withClient("my-less-trusted-autoapprove-client")
                .authorizedGrantTypes("implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write", "trust")
                .autoApprove(true);
        // @formatter:on
    }

//        @Bean
//        public TokenStore tokenStore() {
//            return new InMemoryTokenStore();
//        }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancer)
//                .accessTokenConverter(jwtAccessTokenConverter)
        ;

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm("sparklr-auth-server")
                .tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
//                .tokenKeyAccess("isAnonymous() || permitAll()")
//                .checkTokenAccess("permitAll()");
    }

    @Bean("myTokenEnhancer")
    public TokenEnhancer getTokenEnhancer() {
        return new ApeRiseTokenEnhancer();
    }

    @Bean("jwtAccessTokenConverter")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
//
//    @Bean("jwtTokenStore")
//    public JwtTokenStore jwtTokenStore(@Qualifier("jwtAccessTokenConverter") JwtAccessTokenConverter jwtAccessTokenConverter) {
//        return new JwtTokenStore(jwtAccessTokenConverter);
//    }
//
//    @Value("classpath:auth.sql")
//    private Resource schemaScript;
//
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator());
//        return initializer;
//    }
//
//    private DatabasePopulator databasePopulator() {
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(schemaScript);
//        return populator;
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.109:3306/oauth2");
        dataSource.setUsername("user_rw");
        dataSource.setPassword("root123456");
        return dataSource;
    }


    @Bean("jdbcTokenStore")
    public JdbcTokenStore jdbcTokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    @Lazy
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SparklrUserApprovalHandler userApprovalHandler() throws Exception {
        SparklrUserApprovalHandler handler = new SparklrUserApprovalHandler();
        handler.setApprovalStore(approvalStore());
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        handler.setUseApprovalStore(true);
        return handler;
    }

}