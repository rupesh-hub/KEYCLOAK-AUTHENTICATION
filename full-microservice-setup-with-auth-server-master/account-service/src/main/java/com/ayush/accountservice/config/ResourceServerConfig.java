package com.ayush.accountservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String ROOT_PATTERN = "/**";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/free-api","/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,ROOT_PATTERN).access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST,ROOT_PATTERN).access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT,ROOT_PATTERN).access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE,ROOT_PATTERN).access("#oauth2.hasScope('write')");
    }
}
