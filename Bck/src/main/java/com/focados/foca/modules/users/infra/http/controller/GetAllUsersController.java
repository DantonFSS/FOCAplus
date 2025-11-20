package com.focados.foca.modules.users.infra.http.controller;

import com.focados.foca.modules.users.domain.dtos.response.UserResponseDto;
import com.focados.foca.modules.users.domain.services.GetAllUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para listar todos os usuários
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class GetAllUsersController {

    private final GetAllUsersService getAllUsersService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(getAllUsersService.execute());
    }
}
