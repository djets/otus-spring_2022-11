package ru.otus.spring.hw24.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SecurityConfig {

    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .antMatchers("/books").authenticated()
                                .antMatchers("/books/*").hasRole("ADMIN")
                                .antMatchers("/books/edit/*").hasRole("ADMIN")
                                .antMatchers("/books/*/comments").hasAnyRole("ADMIN", "USER")
                                .anyRequest().permitAll()
                )
                .rememberMe()
                .and()
                .formLogin().permitAll().defaultSuccessUrl("/books")
                .and()
                .logout().permitAll()
                .and()
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());

        return http.build();
    }
}
