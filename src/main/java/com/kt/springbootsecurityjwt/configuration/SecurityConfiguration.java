package com.kt.springbootsecurityjwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.kt.springbootsecurityjwt.authentication.CustomAuthenticationManager;
import com.kt.springbootsecurityjwt.authentication.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder builder)
            throws Exception {
        final var publicEndpoints = new String[] { "/api/public", "/api/login" };
        builder.authenticationProvider(new CustomAuthenticationProvider());
        return http

                // enable cors but disable csrf because we don't need csrf when we use jwt token
                .cors().and().csrf().disable()
                .authorizeHttpRequests().requestMatchers(publicEndpoints).permitAll()
                // NOTE: this casues 403 to unknown routes instead of 404
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()
                        .authenticationManager(this.customAuthenticationManager))
                .build();
    }
}
