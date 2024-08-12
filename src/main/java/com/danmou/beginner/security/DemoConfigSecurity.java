package com.danmou.beginner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.danmou.beginner.service.UserService;

@Configuration
public class DemoConfigSecurity {

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  DaoAuthenticationProvider authenticationProvider(UserService userService) {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userService); // set the custom user details service
    auth.setPasswordEncoder(passwordEncoder()); // set the password encoder - bcrypt
    
    return auth;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(configurer -> configurer
            .requestMatchers("/").hasRole("EMPLOYEE")
            .requestMatchers("/leaders/**").hasRole("MANAGER")
            .requestMatchers("/systems/**").hasRole("ADMIN")
            .requestMatchers("/register/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/custom-logout")
            .logoutSuccessUrl("/login?logout_custom")
            .permitAll())
        .exceptionHandling(configurer -> configurer
            .accessDeniedPage("/access-denied"));

    return http.build();
  }
}
