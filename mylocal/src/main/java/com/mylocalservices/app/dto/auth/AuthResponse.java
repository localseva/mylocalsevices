package com.mylocalservices.app.dto.auth;

import com.mylocalservices.app.enums.auth.worker.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private Role role;
}