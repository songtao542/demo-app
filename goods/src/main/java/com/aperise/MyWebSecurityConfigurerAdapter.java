package com.aperise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("webSecurityConfigurerAdapter")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


    @Autowired
    FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    AccessDecisionManager accessDecisionManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider authenticationProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        return object;
                    }
                })
                .withObjectPostProcessor(new ObjectPostProcessor<MethodSecurityInterceptor>() {
                    @Override
                    public <O extends MethodSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(accessDecisionManager);
                        return object;
                    }
                })
                .antMatchers("/resources/**", "/css/*", "/js/*", "/img/*", "/signin", "/login", "/signup", "/user/add").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/signin")
                .and()
                .logout().logoutSuccessUrl("/signin")
                .and()
                .httpBasic()
                .and()
                .apply(new HttpKeySecretConfigurer<>())
                .and()
                .authenticationProvider(authenticationProvider)
                .userDetailsService(userDetailsService)
        ;

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
//        auth.userDetailsService(userDetailsService);
//        auth.authenticationProvider(authenticationProvider);
//        auth.apply((SecurityConfigurerAdapter) new HttpKeySecretConfigurer<HttpSecurity>());
    }


}
