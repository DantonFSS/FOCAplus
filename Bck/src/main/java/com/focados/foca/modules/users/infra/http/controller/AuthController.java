package com.focados.foca.modules.users.infra.http.controller;

import com.focados.foca.modules.users.domain.dtos.request.CreateUserDto;
import com.focados.foca.modules.users.domain.dtos.request.LoginDto;
import com.focados.foca.modules.users.domain.dtos.request.RefreshTokenDto;
import com.focados.foca.modules.users.domain.dtos.response.AuthResponseDto;
import com.focados.foca.modules.users.domain.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(authService.register(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenDto));
    }
}

