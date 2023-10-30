package com.kt.springbootsecurityjwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.kt.springbootsecurityjwt.authentication.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // prePostEnabled is true by default and securedEnabled and jsr250Enabled are
                      // false by default
public class SecurityConfiguration {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final var securedEndpoints = new String[] { "/api/admin", "/api/secret", "/api/user" };
        return http
                // enable cors but disable csrf because we don't need csrf when we use jwt token
                .cors().and().csrf().disable()
                .authorizeHttpRequests().requestMatchers(securedEndpoints).authenticated()
                // bad practice. in prod, we should permit limited requests and authenticate any
                // requests instead
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // set username add password with custom logic before
                // UsernamePasswordAuthenticationFilter tries to get username and password
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
