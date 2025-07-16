package com.google.legal_sales_sidekick.config;


import com.google.legal_sales_sidekick.app_filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.google.legal_sales_sidekick.constants.constants.UNAUTHORIZED_PATHS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final AuthenticationFilter legalAuthFilter;

    @Autowired
    public WebSecurityConfig(AuthenticationFilter legalAuthFilter) {
        this.legalAuthFilter = legalAuthFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(authz -> authz.requestMatchers(UNAUTHORIZED_PATHS).permitAll()
                .anyRequest().authenticated()).addFilterBefore(legalAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
