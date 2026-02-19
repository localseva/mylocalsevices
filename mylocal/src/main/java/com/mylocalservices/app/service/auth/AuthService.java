package com.mylocalservices.app.service.auth;

import com.mylocalservices.app.dto.auth.LoginRequest;
import com.mylocalservices.app.dto.auth.RegisterRequest;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.enums.auth.worker.Role;
import com.mylocalservices.app.repository.auth.UserRepository;
import com.mylocalservices.app.util.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

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

    public Map<String, Object> login(LoginRequest req) {

        User user = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return Map.of(
                "token", token,
                "role", user.getRole(),
                "userId", user.getId(),
                "name", user.getName()
        );
    }
}
