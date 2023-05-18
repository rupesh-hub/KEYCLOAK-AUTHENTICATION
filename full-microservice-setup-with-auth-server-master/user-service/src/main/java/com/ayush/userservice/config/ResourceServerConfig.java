package com.ayush.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@RequiredArgsConstructor
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String ROOT_PATTERN = "/**";

    private final TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/free-api","/user/register").permitAll()
                .antMatchers(HttpMethod.GET,ROOT_PATTERN).access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST,ROOT_PATTERN).access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT,ROOT_PATTERN).access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE,ROOT_PATTERN).access("#oauth2.hasScope('write')");
    }
}
