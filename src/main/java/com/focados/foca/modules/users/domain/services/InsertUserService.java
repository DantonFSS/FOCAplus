package com.focados.foca.modules.users.domain.services;

import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.dtos.request.CreateUserDto;
import com.focados.foca.modules.users.domain.dtos.response.CreateUserResponseDto;
import com.focados.foca.modules.users.domain.dtos.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsertUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponseDto execute(CreateUserDto dto) {
        // Verifica se email já existe
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        // Mapeia DTO para entidade
        UserModel entity = UserMapper.mappingToUserEntity(dto);

        // Criptografa senha com BCrypt
        entity.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        // Salva usuário
        UserModel savedUser = repository.save(entity);

        // Retorna response
        return UserMapper.mappingToUserResponse(savedUser);
    }
}

