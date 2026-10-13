package com.sunnychanu.cineai.controller;

import com.sunnychanu.cineai.config.JwtUtils;
import com.sunnychanu.cineai.entity.User;
import com.sunnychanu.cineai.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String role = username.toLowerCase().contains("admin") ? "ROLE_ADMIN" : "ROLE_USER";

        String token = jwtUtils.generateToken(username, role);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "tokenType", "Bearer",
                "username", username,
                "role", role,
                "brandEcosystem", "Sunnychanu Technologies"
        ));
    }
}
