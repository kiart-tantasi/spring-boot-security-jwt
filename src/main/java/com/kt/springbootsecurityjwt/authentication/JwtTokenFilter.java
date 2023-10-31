package com.kt.springbootsecurityjwt.authentication;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kt.springbootsecurityjwt.mock.UserDetailsMock;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String tokenWithBearer = request.getHeader("Authorization");
        if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {
            // (concepts: JwtUtils, JwtDecoder, UserDetailsService)
            // simulate validating jwt token to get roles from DB
            final var token = tokenWithBearer.split(" ")[1];
            if (token.equals("ADMIN") || token.equals("USER")) {
                final var isAdmin = token.equals("ADMIN");
                final var authenMock = UserDetailsMock.getAuthenMock(isAdmin);
                SecurityContextHolder.getContext().setAuthentication(authenMock);
                System.out.println("authen was set with authorities " + authenMock.getAuthorities());
                System.out.println("=======================");
                filterChain.doFilter(request, response);
                return;
            }
        }
        SecurityContextHolder.clearContext();
        System.out.println("authen was cleared because of invalid authentication");
        System.out.println("=======================");
        filterChain.doFilter(request, response);
        return;
    }

}
