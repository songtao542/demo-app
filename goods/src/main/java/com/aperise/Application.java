package com.aperise;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


@ImportResource(locations = {"application-context.xml"})
@MapperScan("com.aperise.mapper")
@SpringBootApplication
@EnableGlobalMethodSecurity
@EnableWebSecurity
public class Application implements EmbeddedServletContainerCustomizer {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8081);
        container.setSessionTimeout(30, TimeUnit.MINUTES);
    }


    @Bean
    public EmbeddedServletContainerFactory getEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(8081);
        factory.setSessionTimeout(30, TimeUnit.MINUTES);
        return factory;
    }

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
        WebSecurityConfigurerAdapter adapter = new WebSecurityConfigurerAdapter() {
        };
        return adapter;
    }

    @Bean
    public AuthorizationServerConfigurer getAuthorizationServerConfigurer() {
        AuthorizationServerConfigurer configurer = new AuthorizationServerConfigurerAdapter() {

        };
        return configurer;
    }

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