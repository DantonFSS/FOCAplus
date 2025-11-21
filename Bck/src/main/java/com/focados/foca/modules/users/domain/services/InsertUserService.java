package com.focados.foca.modules.users.domain.services;

import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.dtos.request.CreateUserDto;
import com.focados.foca.modules.users.domain.dtos.response.CreateUserResponseDto;
import com.focados.foca.modules.users.domain.dtos.mappers.UserMapper;
import com.focados.foca.shared.common.utils.exceptions.EmailAlreadyUsedException;
import com.focados.foca.shared.common.utils.exceptions.UsernameAlreadyExistsException;
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
            throw new EmailAlreadyUsedException(dto.getEmail());
        }

        // Verifica se username já existe
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(dto.getUsername());
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

