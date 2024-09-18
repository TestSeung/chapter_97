package com.github.supercoding.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers((headerConfig)->headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin((formLoginConfig)->formLoginConfig.disable())
                .csrf((csrfConfig)->csrfConfig.disable())
                .httpBasic((httpBasicConfig)->httpBasicConfig.disable())
                .rememberMe((rememberMeConfig)->rememberMeConfig.disable())
                .sessionManagement((sessionManagementConfig)->
                        sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
