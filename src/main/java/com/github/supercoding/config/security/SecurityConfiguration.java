package com.github.supercoding.config.security;

import com.github.supercoding.web.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers((headerConfig)->headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin((formLoginConfig)->formLoginConfig.disable())
                .csrf((csrfConfig)->csrfConfig.disable())
                .cors((corsConfig)->corsConfig.configurationSource(corsConfigurationSource())) //chaining 등록
                .httpBasic((httpBasicConfig)->httpBasicConfig.disable())
                .rememberMe((rememberMeConfig)->rememberMeConfig.disable())
                .sessionManagement((sessionManagementConfig)->
                        sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests)->authorizeRequests
                        .requestMatchers("/resources/static/**","/v1/api/sign/*").permitAll()
                        .requestMatchers("/v1/api/air-reservation/*").hasRole("USER")
                )
                .exceptionHandling((exceptionConfig)->exceptionConfig
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomerAccessDeniedHandler()))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:63342"));
        configuration.setAllowCredentials(true); //token 주고 받을 때
        configuration.addExposedHeader("X-AUTH_TOKEN"); //token이 잘 주고받기가 안될때
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","PUT","POST","PATCH","DELETE","OPTIONS"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
