package com.kt.springbootsecurityjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // simulate comparing username and password in db
        if ((!request.username().equals("user") && !request.username().equals("admin"))
                || !request.password().equals("password")) {
            return new ResponseEntity<>("not found", HttpStatus.BAD_REQUEST);
        }
        // simulate encoding token and send back to user
        final var token = request.username().equals("admin") ? "Bearer ADMIN" : "Bearer USER";
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}

// TODO: move to models dir
record LoginRequest(String username, String password) {
};
