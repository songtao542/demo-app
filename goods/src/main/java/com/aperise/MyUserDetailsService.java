package com.aperise;


import com.aperise.bean.Role;
import com.aperise.bean.User;
import com.aperise.controller.UserController;
import com.aperise.mapper.RoleMapper;
import com.aperise.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    protected static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername username-->" + username);
        User user = userMapper.selectByName(username);
        logger.debug("loadUserByUsername user-->" + user);
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
            String role = user.getRole() == null ? "ROLE_USER" : user.getRole().getName();
            authorities.add(new SimpleGrantedAuthority(role));
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
                return String.valueOf(user.getId());
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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("username:" + getUsername());
            sb.append("password:" + getPassword());
            Collection<? extends GrantedAuthority> authorities = getAuthorities();
            if (authorities != null) {
                sb.append("authorities:[");
                for (GrantedAuthority auth : authorities) {
                    sb.append(auth.getAuthority());
                    sb.append(",");
                }
                sb.append("]");
            }
            return sb.toString();
        }
    }

}
