package com.example.nhom2_st22b_web2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Public access
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                
                // Role-based access
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/users/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/companies/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                .requestMatchers("/members/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                
                // Specific endpoints
                .requestMatchers("/add-user", "/edit-user/**", "/delete-user/**").hasRole("ADMIN")
                .requestMatchers("/add-company", "/edit-company/**", "/delete-company/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/add-member", "/edit-member/**", "/delete-member/**").hasAnyRole("ADMIN", "MANAGER")
                
                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .userDetailsService(userDetailsService)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions().sameOrigin()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 