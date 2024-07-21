package com.opensell.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppConfig appConfig;

    public static final String[] WHITE_LIST = {
        "/**"
    };

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return (request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedHeaders(List.of("*"));
            corsConfiguration.setAllowedMethods(List.of("*"));
            corsConfiguration.setAllowedOrigins(appConfig.allowedUrls());
            return corsConfiguration;
        });
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(appConfig.allowedUrls());
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(customizer -> customizer
                .requestMatchers(WHITE_LIST).permitAll()
            )
            .cors(cors -> corsConfigurationSource())
            .build();
    }
}
