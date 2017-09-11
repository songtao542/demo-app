package com.aperise;


import com.aperise.bean.User;
import com.aperise.controller.UserController;
import com.aperise.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class MyUserDetailsService implements UserDetailsService {
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername-->" + username);
        User user = userMapper.selectByName(username);
        logger.debug("loadUserByUsername-->" + user);
        UserDetails details = new MyUserDetails(user);
        return details;
    }


    public static class MyUserDetails implements UserDetails {

        User user;

        public MyUserDetails(User user) {
            this.user = user;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MyGrantedAuthority("*"));
            return authorities;
        }

        @Override
        public String getPassword() {
            if (user != null) {
                return user.getPassword();
            }
            return "";
        }

        @Override
        public String getUsername() {
            if (user != null) {
                return String.valueOf(user.getName());
            }
            return "";
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


    public static class MyGrantedAuthority implements GrantedAuthority {

        String authority;

        public MyGrantedAuthority(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
