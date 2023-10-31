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
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final var publicEndpoints = new String[] { "/api/public", "/api/login" };
        return http
                // enable cors but disable csrf because we don't need csrf when we use jwt token
                .cors().and().csrf().disable()
                .authorizeHttpRequests().requestMatchers(publicEndpoints).permitAll()
                // NOTE: this casues 403 to unknown routes instead of 404
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // [why before UsernamePasswordAuthenticationFilter ?]
                // ref: https://stackoverflow.com/a/70885651/21331113
                // if you place your filter where the UsernamePasswordAuthenticationFilter would
                // be, any possible exception thrown by your filter will not be seen by the
                // ExceptionTranslatorFilter, instead it will go all the way back to the
                // throwable() method of Tomcat's
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
