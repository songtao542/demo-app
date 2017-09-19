package com.aperise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

@Component("webSecurityConfigurerAdapter")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


    @Autowired
    FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    AccessDecisionManager accessDecisionManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable();
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        //object.setAccessDecisionManager(accessDecisionManager);
//                        object.setSecurityMetadataSource(securityMetadataSource);
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
                .antMatchers("/resources/**", "/css/*", "/js/*", "/img/*","/", "/signin", "/login", "/signup", "/user/add").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
        //.loginProcessingUrl("/login")
        ;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService((MyUserDetailsService) ApplicationContextHolder.getBean("userDetailsService"));
    }


}
