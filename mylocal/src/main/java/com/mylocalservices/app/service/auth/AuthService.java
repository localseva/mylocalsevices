package com.mylocalservices.app.service.auth;

import com.mylocalservices.app.dto.auth.AuthResponse;
import com.mylocalservices.app.dto.auth.LoginRequest;
import com.mylocalservices.app.dto.auth.RegisterRequest;
import com.mylocalservices.app.entity.auth.RefreshToken;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.enums.auth.worker.Role;
import com.mylocalservices.app.repository.auth.RefreshTokenRepository;
import com.mylocalservices.app.repository.auth.UserRepository;
import com.mylocalservices.app.util.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenRepository refreshRepo;

    @Autowired
    private RefreshTokenService refreshTokenService;

    // ================= REGISTER =================

    public String register(RegisterRequest req) {

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(Role.valueOf(req.getRole()));

        repo.save(user);

        return "User registered successfully";
    }

    // ================= LOGIN =================

    public AuthResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 🔐 Access Token
        String accessToken = jwtUtil.generateToken(user.getEmail());

        // 🔁 Refresh Token
        String refreshToken = UUID.randomUUID().toString();

        // Remove old token if exists
        refreshTokenService.createRefreshToken(user, refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    // ================= REFRESH TOKEN =================

    public Map<String, Object> refreshToken(String refreshToken) {

        RefreshToken storedToken = refreshRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Session Expired"));

        if (storedToken.getExpiryDate().isBefore(Instant.now())) {
            refreshRepo.delete(storedToken);
            throw new RuntimeException("Session expired");
        }

        User user = storedToken.getUser();

        String newAccessToken = jwtUtil.generateToken(user.getEmail());

        return Map.of(
                "accessToken", newAccessToken,
                "refreshToken", refreshToken
        );
    }

    // ================= LOGOUT =================

    public void logout(String refreshToken) {

        refreshRepo.deleteByToken(refreshToken);
    }
}
