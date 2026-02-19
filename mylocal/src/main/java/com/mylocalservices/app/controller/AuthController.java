//package com.mylocalservices.app.controller;
//
//import com.mylocalservices.app.dto.request.LoginRequest;
//import com.mylocalservices.app.dto.request.RegisterRequest;
//import com.mylocalservices.app.dto.request.RefreshRequest;
//import com.mylocalservices.app.dto.response.AuthResponse;
//import com.mylocalservices.app.service.AuthService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) { this.authService = authService; }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
//        return ResponseEntity.ok(authService.register(req));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
//        return ResponseEntity.ok(authService.login(req));
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest req) {
//        return ResponseEntity.ok(authService.refreshToken(req));
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestBody RefreshRequest req) {
//        authService.logout(req.getRefreshToken());
//        return ResponseEntity.ok().build();
//    }
//}
