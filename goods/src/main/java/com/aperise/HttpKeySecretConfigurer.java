package com.aperise;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

public class HttpKeySecretConfigurer<B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<HttpKeySecretConfigurer<B>, B> {
//public class HttpKeySecretConfigurer extends AbstractHttpConfigurer<HttpKeySecretConfigurer, HttpSecurity> {

    private static final RequestHeaderRequestMatcher X_REQUESTED_WITH = new RequestHeaderRequestMatcher("X-Requested-With",
            "XMLHttpRequest");

    private static final String DEFAULT_REALM = "Realm";

    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    private KeySecretAuthenticationEntryPoint keySecretAuthEntryPoint = new KeySecretAuthenticationEntryPoint();

    /**
     * Creates a new instance
     *
     * @throws Exception
     */
    public HttpKeySecretConfigurer() throws Exception {
        realmName(DEFAULT_REALM);

        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<RequestMatcher, AuthenticationEntryPoint>();
        entryPoints.put(X_REQUESTED_WITH, new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        DelegatingAuthenticationEntryPoint defaultEntryPoint = new DelegatingAuthenticationEntryPoint(
                entryPoints);
        defaultEntryPoint.setDefaultEntryPoint(this.keySecretAuthEntryPoint);
        this.authenticationEntryPoint = defaultEntryPoint;
    }

    /**
     * Allows easily changing the realm, but leaving the remaining defaults in place. If
     * {@link #authenticationEntryPoint(AuthenticationEntryPoint)} has been invoked,
     * invoking this method will result in an error.
     *
     * @param realmName the HTTP Basic realm to use
     * @return {@link HttpKeySecretConfigurer} for additional customization
     * @throws Exception
     */
    public HttpKeySecretConfigurer<B> realmName(String realmName) throws Exception {
        this.keySecretAuthEntryPoint.setRealmName(realmName);
        this.keySecretAuthEntryPoint.afterPropertiesSet();
        return this;
    }

    /**
     * The {@link AuthenticationEntryPoint} to be populated on
     * {@link KeySecretAuthenticationFilter} in the event that authentication fails. The
     * default to use {@link KeySecretAuthenticationEntryPoint} with the realm
     * "Spring Security Application".
     *
     * @param authenticationEntryPoint the {@link AuthenticationEntryPoint} to use
     * @return {@link HttpKeySecretConfigurer} for additional customization
     */
    public HttpKeySecretConfigurer<B> authenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        return this;
    }

    /**
     * Specifies a custom {@link AuthenticationDetailsSource} to use for basic
     * authentication. The default is {@link WebAuthenticationDetailsSource}.
     *
     * @param authenticationDetailsSource the custom {@link AuthenticationDetailsSource}
     *                                    to use
     * @return {@link HttpKeySecretConfigurer} for additional customization
     */
    public HttpKeySecretConfigurer<B> authenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
        return this;
    }

    @Override
    public void init(B http) throws Exception {
        registerDefaults(http);
    }

    private void registerDefaults(B http) {
        ContentNegotiationStrategy contentNegotiationStrategy = http
                .getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }

        MediaTypeRequestMatcher restMatcher = new MediaTypeRequestMatcher(
                contentNegotiationStrategy, MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_XML,
                MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_XML);
        restMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));

        RequestMatcher notHtmlMatcher = new NegatedRequestMatcher(
                new MediaTypeRequestMatcher(contentNegotiationStrategy,
                        MediaType.TEXT_HTML));
        RequestMatcher restNotHtmlMatcher = new AndRequestMatcher(
                Arrays.<RequestMatcher>asList(notHtmlMatcher, restMatcher));

        RequestMatcher preferredMatcher = new OrRequestMatcher(Arrays.asList(X_REQUESTED_WITH, restNotHtmlMatcher));

        registerDefaultEntryPoint(http, preferredMatcher);
        registerDefaultLogoutSuccessHandler(http, preferredMatcher);
    }

    private void registerDefaultEntryPoint(B http, RequestMatcher preferredMatcher) {
        ExceptionHandlingConfigurer<B> exceptionHandling = http
                .getConfigurer(ExceptionHandlingConfigurer.class);
        if (exceptionHandling == null) {
            return;
        }
        exceptionHandling.defaultAuthenticationEntryPointFor(
                postProcess(this.authenticationEntryPoint), preferredMatcher);
    }

    private void registerDefaultLogoutSuccessHandler(B http, RequestMatcher preferredMatcher) {
        LogoutConfigurer<B> logout = http
                .getConfigurer(LogoutConfigurer.class);
        if (logout == null) {
            return;
        }
        LogoutConfigurer<B> handler = logout.defaultLogoutSuccessHandlerFor(
                postProcess(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT)), preferredMatcher);
    }

    @Override
    public void configure(B http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        KeySecretAuthenticationFilter keySecretAuthenticationFilter = new KeySecretAuthenticationFilter(authenticationManager, this.authenticationEntryPoint);
        if (this.authenticationDetailsSource != null) {
            keySecretAuthenticationFilter.setAuthenticationDetailsSource(this.authenticationDetailsSource);
        }
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        if (rememberMeServices != null) {
            keySecretAuthenticationFilter.setRememberMeServices(rememberMeServices);
        }
        keySecretAuthenticationFilter = postProcess(keySecretAuthenticationFilter);
        http.addFilterBefore(keySecretAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
