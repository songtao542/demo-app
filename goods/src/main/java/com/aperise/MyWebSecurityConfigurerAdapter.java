package com.aperise;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//                super.configure(http);
        http.authorizeRequests()
                .antMatchers("/resources/**", "/css/*", "/js/*", "/img/*", "/signin", "/login", "/signup").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
//                        .loginProcessingUrl("/login")
        ;
    }
}
