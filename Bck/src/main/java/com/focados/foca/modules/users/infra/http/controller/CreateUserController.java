package com.focados.foca.modules.users.infra.http.controller;

import com.focados.foca.modules.users.domain.dtos.request.CreateUserDto;
import com.focados.foca.modules.users.domain.dtos.response.CreateUserResponseDto;
import com.focados.foca.modules.users.domain.services.InsertUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class CreateUserController {
    private final InsertUserService insertUserService;

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> create(@RequestBody CreateUserDto request) {
        return ResponseEntity.ok(insertUserService.execute(request));
    }
}

