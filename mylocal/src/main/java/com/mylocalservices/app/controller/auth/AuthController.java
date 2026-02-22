package com.mylocalservices.app.controller.auth;

import com.mylocalservices.app.dto.auth.AuthResponse;
import com.mylocalservices.app.dto.auth.LoginRequest;
import com.mylocalservices.app.dto.auth.RefreshTokenRequest;
import com.mylocalservices.app.dto.auth.RegisterRequest;
import com.mylocalservices.app.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

    /**
     * 🔄 Refresh Access Token using Refresh Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                service.refreshToken(request.getRefreshToken())
        );
    }

    /**
     * 🚪 Logout — invalidate refresh token
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody RefreshTokenRequest request) {

        service.logout(request.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }
}
