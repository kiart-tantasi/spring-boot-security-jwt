package com.kt.springbootsecurityjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class UserController {
    @GetMapping
    public String get() {
        return "THIS ROUTE IS FOR ANY AUTHENTICATED USER";
    }
}
