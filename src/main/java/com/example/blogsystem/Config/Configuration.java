package com.example.blogsystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/register-admin",
                                "/api/v1/blog/admin/get-all").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/blog/get-all",
                                "/api/v1/blog/add",
                                "/api/v1/blog/update/**",
                                "/api/v1/blog/delete/**",
                                "/api/v1/blog/title/**",
                                "/api/v1/blog/*").hasAuthority("USER")
                        .requestMatchers("/api/v1/auth/update",
                                "/api/v1/auth/delete").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()

                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .invalidateHttpSession(true)
                )
                .httpBasic( basic -> {})
                .build();


    }

}
