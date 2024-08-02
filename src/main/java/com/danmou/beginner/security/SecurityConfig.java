package com.danmou.beginner.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  UserDetailsManager userDetailsManager(DataSource dataSource){
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(configurer -> configurer
        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("MANAGER")
        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN"));

    http.httpBasic(Customizer.withDefaults());
    http.csrf(crsf -> crsf.disable());

    return http.build();
  }
}
