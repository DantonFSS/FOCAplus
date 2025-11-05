package com.focados.foca.modules.users.domain.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    @NotNull
    @NotBlank(message = "Email must not be empty or null...")
    private String email;

    @NotNull
    @NotBlank(message = "Password must not be empty or null...")
    private String password;
}

