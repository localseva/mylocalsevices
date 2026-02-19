//package com.mylocalservices.app.service;
//
//import com.mylocalservices.app.dto.request.LoginRequest;
//import com.mylocalservices.app.dto.request.RegisterRequest;
//import com.mylocalservices.app.dto.request.RefreshRequest;
//import com.mylocalservices.app.dto.response.AuthResponse;
//import com.mylocalservices.app.entity.RefreshToken;
//import com.mylocalservices.app.entity.User;
//import com.mylocalservices.app.entity.UserRole;
//import com.mylocalservices.app.exception.ResourceNotFoundException;
//import com.mylocalservices.app.repository.RefreshTokenRepository;
//import com.mylocalservices.app.repository.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.UUID;
//
//@Service
//public class AuthService {
//
//    private final UserRepository userRepository;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final JwtService jwtService;
//    private final PasswordEncoder passwordEncoder;
//    private final long refreshValiditySec;
//
//    public AuthService(
//            UserRepository userRepository,
//            RefreshTokenRepository refreshTokenRepository,
//            JwtService jwtService,
//            PasswordEncoder passwordEncoder,
//            // read validity for rotation if desired
//            @org.springframework.beans.factory.annotation.Value("${app.jwt.refresh-token-validity-sec}") long refreshValiditySec
//    ) {
//        this.userRepository = userRepository;
//        this.refreshTokenRepository = refreshTokenRepository;
//        this.jwtService = jwtService;
//        this.passwordEncoder = passwordEncoder;
//        this.refreshValiditySec = refreshValiditySec;
//    }
//
//    public AuthResponse register(RegisterRequest req) {
//        if (userRepository.findByPhone(req.getPhone()).isPresent()) {
//            throw new RuntimeException("Phone already registered");
//        }
//
//        User user = User.builder()
//                .phone(req.getPhone())
//                .name(req.getName())
//                .password(passwordEncoder.encode(req.getPassword()))
//                .role(req.getRole() == null
//                        ? UserRole.USER
//                        : UserRole.valueOf(req.getRole()))
//                .build();
//
//        userRepository.save(user);
//
//        String access = jwtService.generateAccessToken(user);
//        String refresh = createAndSaveRefreshToken(user);
//
//        return AuthResponse.builder()
//                .accessToken(access)
//                .refreshToken(refresh)
//                .userId(user.getId())
//                .role(user.getRole().name())
//                .build();
//    }
//
//
//    public AuthResponse login(LoginRequest req) {
//        User user = userRepository.findByPhone(req.getPhone())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        // rotate: delete previous refresh tokens
//        refreshTokenRepository.deleteByUser(user);
//
//        String access = jwtService.generateAccessToken(user);
//        String refresh = createAndSaveRefreshToken(user);
//
//        return AuthResponse.builder()
//                .accessToken(access)
//                .refreshToken(refresh)
//                .userId(user.getId())
//                .role(user.getRole().name())
//                .build();
//    }
//
//    private String createAndSaveRefreshToken(User user) {
//        String token = UUID.randomUUID().toString() + "-" + jwtService.generateRefreshToken(user);
//        RefreshToken rt = RefreshToken.builder()
//                .token(token)
//                .user(user)
//                .expiryDate(Instant.now().plusSeconds(refreshValiditySec))
//                .revoked(false)
//                .build();
//        refreshTokenRepository.save(rt);
//        return token;
//    }
//
//    public AuthResponse refreshToken(RefreshRequest req) {
//        String incoming = req.getRefreshToken();
//        RefreshToken rt = refreshTokenRepository.findByToken(incoming)
//                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//
//        if (rt.isRevoked() || rt.getExpiryDate().isBefore(Instant.now())) {
//            throw new RuntimeException("Refresh token expired or revoked");
//        }
//
//        User user = rt.getUser();
//        // rotate: revoke old token & create new
//        rt.setRevoked(true);
//        refreshTokenRepository.save(rt);
//
//        String newAccess = jwtService.generateAccessToken(user);
//        String newRefresh = createAndSaveRefreshToken(user);
//
//        return AuthResponse.builder()
//                .accessToken(newAccess)
//                .refreshToken(newRefresh)
//                .userId(user.getId())
//                .role(user.getRole().name())
//                .build();
//    }
//
//    public void logout(String refreshToken) {
//        refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
//            rt.setRevoked(true);
//            refreshTokenRepository.save(rt);
//        });
//    }
//}
