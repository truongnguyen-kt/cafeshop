package com.kt.cafeshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.kt.cafeshop.service.impl.JwtUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().permitAll();
                    // auth.requestMatchers("/cafeshop/auth/**").permitAll();
                    // auth.requestMatchers("/cafeshop/account/**").permitAll();
                    // auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SimpleCORSFilter(), WebAsyncManagerIntegrationFilter.class);
        // .authorizeHttpRequests(auth -> auth
        // .requestMatchers("*/*").permitAll()
        // .anyRequest().authenticated())
        // .sessionManagement(sess ->
        // sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // .authenticationProvider(authenticationProvider());

        // http.addFilterBefore(jwtRequestFilter,
        // UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
