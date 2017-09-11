package com.aperise;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableGlobalMethodSecurity
@EnableWebSecurity
@ImportResource(locations = {"application-context.xml"})
@MapperScan({"com.aperise.mapper"})
@Configuration
public class MyConfiguration {

    protected static Logger logger = LoggerFactory.getLogger(MyConfiguration.class);

    SecurityAutoConfiguration a;
//    @Bean
//    public WebMvcConfigurer getWebMvcConfigurer() {
//        return new WebConfig();
//    }

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
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public WebSecurityConfigurerAdapter getWebSecurityConfigurerAdapter() {
        logger.debug("---------------------getWebSecurityConfigurerAdapter---------------------");
        return new MyWebSecurityConfigurerAdapter();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        logger.debug("---------------------getUserDetailsService---------------------");
        return new MyUserDetailsService();
    }

//    @Bean
//    public MyAuthenticationProvider springAuthenticationProvider() {
//        return new MyAuthenticationProvider();
//    }

//    @Bean
//    public AuthorizationServerConfigurer getAuthorizationServerConfigurer() {
//        AuthorizationServerConfigurer configurer = new AuthorizationServerConfigurerAdapter() {
//
//        };
//        return configurer;
//    }

//    @Bean
//    public SqlSessionFactory getSqlSessionFactory() {
//        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//            return sqlSessionFactory;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Bean
//    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        if (sqlSessionFactory != null) {
//            System.out.println("sqlSessionFactory1==" + sqlSessionFactory);
//            return new SqlSessionTemplate(sqlSessionFactory);
//        } else {
//            return null;
//        }
//    }
}
