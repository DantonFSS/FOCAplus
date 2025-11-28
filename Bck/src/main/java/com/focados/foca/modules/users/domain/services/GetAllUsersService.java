package com.focados.foca.modules.users.domain.services;

import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.dtos.response.UserResponseDto;
import com.focados.foca.modules.users.domain.dtos.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsersService {

    private final UserRepository userRepository;

    public List<UserResponseDto> execute() {
        var users = userRepository.findAll();
        return UserMapper.mappingToUserReponseList(users);
    }
}