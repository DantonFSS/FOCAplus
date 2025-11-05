package com.focados.foca.modules.users.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @NotNull
    @NotBlank(message = "Refresh token must not be empty or null...")
    private String refreshToken;
}

