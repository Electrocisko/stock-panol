package com.panol.stock.config;

import com.panol.stock.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegister() {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
