package com.aperise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

public class KeySecretAuthenticationProvider implements AuthenticationProvider {
    protected static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (logger.isDebugEnabled())
            logger.debug("authenticate:" + authentication);
        /**TODO 提供根据key-secret认证的逻辑，KeySecretAuthenticationToken中会保存用户key,和secret签名信息，secret不得在网络上传输
         具体实现方式就是：1,取得用户key之后，通过查询数据库取得用户secret,并通过同样的签名算法取得签名,然后和用户传过来的签名比较*/
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if (logger.isDebugEnabled())
            logger.debug("supports:" + authentication);
        return (KeySecretAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
