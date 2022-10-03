// package com.midorimart.managementsystem.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class WebSecurityConfig {
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//         httpSecurity.csrf().disable().authorizeRequests().anyRequest().permitAll().and().sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//         return httpSecurity.build();
//     }
// }
