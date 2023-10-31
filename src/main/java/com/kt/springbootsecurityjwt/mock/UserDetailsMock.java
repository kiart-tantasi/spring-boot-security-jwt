package com.kt.springbootsecurityjwt.mock;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsMock {
    public static Authentication getAuthenMock(boolean isAdmin) {
        final UserDetails userDetailsMock = getUserDetailsMock(isAdmin);
        return new UsernamePasswordAuthenticationToken(
                userDetailsMock, null,
                userDetailsMock.getAuthorities());

    }

    private static UserDetails getUserDetailsMock(boolean isAdmin) {
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
}
