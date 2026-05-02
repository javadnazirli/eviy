package com.eviy.controller;

import com.eviy.entity.User;
import com.eviy.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request.name(), request.email(), request.password());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    public record RegisterRequest(
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {}

    public record LoginRequest(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {}

    public record AuthResponse(
            @JsonProperty("token") String token
    ) {}
}