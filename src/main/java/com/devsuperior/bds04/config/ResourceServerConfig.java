package com.devsuperior.bds04.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    final JwtTokenStore tokenStore;
    final Environment env;

    private static final String[] PUBLIC = {"/oauth/token", "/h2-console/**"};

    private static final String[] NOT_LOGGED_IN = {"/events/**", "/cities/**"};

    private static final String[] CLIENT_OR_ADMIN = {"/events/**"};

    private static final String[] ADMIN = {"/cities/**", "/users/**", "/events/**"};

    public ResourceServerConfig(JwtTokenStore tokenStore, Environment env) {
        this.tokenStore = tokenStore;
        this.env = env;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //H2
        if (Arrays.asList(env.getActiveProfiles()).contains("test")){
            http.headers().frameOptions().disable();
        };

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(HttpMethod.GET, NOT_LOGGED_IN).permitAll()
                .antMatchers(HttpMethod.POST, CLIENT_OR_ADMIN).hasAnyRole("CLIENT", "ADMIN")
                .antMatchers(ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}