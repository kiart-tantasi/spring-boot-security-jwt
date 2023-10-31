package com.kt.springbootsecurityjwt.authentication;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

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
            // TODO: check if token is valid and not expired
            // TODO: look for user in repository (DB) and get user-info, authorities, etc
            // OPTIONAL: set request as details into request

            // simulate validating jwt token to get roles from DB
            final var token = tokenWithBearer.split(" ")[1];
            if (token.equals("ADMIN") || token.equals("USER")) {
                final var isAdmin = token.equals("ADMIN");
                final var authenMock = this.getAuthenMock(isAdmin);
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

    // TODO: implement user details logic
    private UserDetails getUserDetailsMock(boolean isAdmin) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(isAdmin ? "ADMIN" : "USER"));
            }

            @Override
            public String getPassword() {
                return "PASSWORD";
            }

            @Override
            public String getUsername() {
                return "USERNAME";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    private Authentication getAuthenMock(boolean isAdmin) {
        UserDetails userDetailsMock = this.getUserDetailsMock(isAdmin);
        return new UsernamePasswordAuthenticationToken(
                userDetailsMock, null,
                userDetailsMock.getAuthorities());
    }
    // ==================================
}
