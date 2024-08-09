package com.danmou.beginner.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoConfigSecurity {

  @Bean
  UserDetailsManager userDetailsManager(DataSource dataSource) {
    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
    userDetailsManager.setUsersByUsernameQuery("SELECT user_id_, pw, active FROM members WHERE user_id=?");
    userDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_id, role FROM roles WHERE user_id=?");

    return userDetailsManager;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(configurer -> configurer
            .requestMatchers("/").hasRole("EMPLOYEE")
            .requestMatchers("/leaders/**").hasRole("MANAGER")
            .requestMatchers("/systems/**").hasRole("ADMIN")
            .anyRequest()
            .authenticated())
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
