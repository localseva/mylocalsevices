package com.mylocalservices.app.repository.auth;

import com.mylocalservices.app.entity.auth.RefreshToken;
import com.mylocalservices.app.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    void deleteByToken(String token);
}