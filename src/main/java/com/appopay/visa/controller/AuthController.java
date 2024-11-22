package com.appopay.visa.controller;


import com.appopay.visa.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    @Value("${static.username}")
    private String STATIC_USERNAME;
    @Value("${static.password}")
    private String STATIC_PASSWORD;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (STATIC_USERNAME.equals(username) && STATIC_PASSWORD.equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok().body("Bearer " + token);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }

        String oldToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
        String username;

        try {
            // Extract username even from expired tokens
            username = jwtUtil.extractUsernameFromExpiredToken(oldToken);
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(403).body("Invalid token");
        }

        // Generate a new token
        String newToken = jwtUtil.generateToken(username);

        return ResponseEntity.ok(newToken);
    }
}
