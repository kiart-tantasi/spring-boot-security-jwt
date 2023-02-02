package com.kt.springbootsecurityjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secret")
public class SecretController {

    @GetMapping
    public String get() {
        return "THIS IS MESSAGE FROM SECRET SERVICE";
    }
}
