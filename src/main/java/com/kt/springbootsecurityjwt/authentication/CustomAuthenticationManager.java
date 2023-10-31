package com.kt.springbootsecurityjwt.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

// AuthenticationManager that got by default from Spring will for-loop AuthenticationProvider to authenticate one-by-one

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (final var provider : getProviders()) {
            final var authen = provider.authenticate(authentication);
            if (authen.isAuthenticated()) {
                return authen;
            }
        }
        return authentication;
    }

    private AuthenticationProvider[] getProviders() {
        return new AuthenticationProvider[] { this.customAuthenticationProvider };
    }
}
