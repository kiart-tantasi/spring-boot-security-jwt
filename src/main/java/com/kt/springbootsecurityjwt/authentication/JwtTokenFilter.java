package com.kt.springbootsecurityjwt.authentication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationProviderMock authenticationProviderMock;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String tokenWithBearer = request.getHeader("Authorization");
        if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {
            final String token = tokenWithBearer.substring(7);
            System.out.println("token: " + token);

            // extract user from token

            // check if auth is null
            // if not null, do nothing
            // if null, get user from database

            // get user from database (by User Detail Service to get UserDetails)
            // check if info in token and info in user is matches
            // and check if token expires

            // when got data from database, set user details and authorities into token
            // optional: set request as details into request

            // set token into security holder's authentication
            final var authen = authenticationProviderMock.getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authen);
            System.out.println("isAuthen: " + authen.isAuthenticated());
            System.out.println("authen was set");
        } else {
            SecurityContextHolder.clearContext();
            System.out.println("context was clearer");
        }
        filterChain.doFilter(request, response);
    }
}
