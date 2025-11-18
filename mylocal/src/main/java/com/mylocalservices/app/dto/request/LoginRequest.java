package com.mylocalservices.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String phone;
    @NotNull
    private String password;
}
