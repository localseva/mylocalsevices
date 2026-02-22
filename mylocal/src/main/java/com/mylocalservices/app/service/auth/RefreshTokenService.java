package com.mylocalservices.app.service.auth;

import com.mylocalservices.app.entity.auth.RefreshToken;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.repository.auth.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshRepo;

    @Transactional
    public void createRefreshToken(User user, String refreshToken) {

        refreshRepo.deleteByUser(user);
        refreshRepo.flush();

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUser(user);
        token.setExpiryDate(
                Instant.now().plus(12, ChronoUnit.HOURS)
        );

        refreshRepo.save(token);
    }
}