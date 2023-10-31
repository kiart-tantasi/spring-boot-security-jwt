package com.kt.springbootsecurityjwt.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import com.kt.springbootsecurityjwt.mock.UserDetailsMock;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        // same as authentication.getPrincipal()
        final String token = ((BearerTokenAuthenticationToken) authentication).getToken();
        if (!token.equals("ADMIN") && !token.equals("USER")) {
            System.out.println("token is invalid");
            return authentication;
        }
        System.out.println("token is valid");
        final var authenMock = UserDetailsMock.getAuthenMock(token.equals("ADMIN"));
        return authenMock;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(Authentication.class);
    }
}
