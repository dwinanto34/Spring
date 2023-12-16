package com.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AppSecurityConfig {
//    First option : The user accounts are hard-coded in source code

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails andy = User.builder()
//                .username("andy")
//                noop is the encoding algorithm ID
//                while andypassword is the password
//                .password("{noop}andypassword")
//                .roles("EMPLOYEE")
//                .build();

//        UserDetails tom = User.builder()
//                .username("tom")
//                .password("{noop}tompassword")
//                .roles("EMPLOYEE", "SUPERVISOR")
//                .build();

//        UserDetails mike = User.builder()
//                .username("mike")
//                .password("{noop}mikepassword")
//                .roles("EMPLOYEE", "SUPERVISOR", "MANAGER")
//                .build();

//        return new InMemoryUserDetailsManager(andy, tom, mike);
//    }

//    Second option : The user accounts are stored in database
//    Below implementation requires us to follow the exact table and column names
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

//    Second option : The user accounts are stored in database
//    Below implementation allows us to have custom table and column names
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled " +
                "FROM users " +
                "WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority " +
                "FROM authorities " +
                "WHERE username = ?");

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/products").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/products/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/products").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("MANAGER")
                        .anyRequest().denyAll());

        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
