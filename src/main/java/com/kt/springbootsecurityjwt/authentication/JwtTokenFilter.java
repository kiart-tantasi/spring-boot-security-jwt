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

    private final String AUTHEN_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String fullToken = request.getHeader(this.AUTHEN_HEADER);
        if (fullToken != null && fullToken.substring(0, 7).equals("Bearer ")) {
            final String subToken = fullToken.substring(7);

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

            SecurityContextHolder.getContext()
                    .setAuthentication(subToken.equals("test") ? authenticationProviderMock.getAuthentication()
                            : authenticationProviderMock.getInvalidAuthentication());
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
