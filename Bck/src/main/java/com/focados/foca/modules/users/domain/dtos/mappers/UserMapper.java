package com.focados.foca.modules.users.domain.dtos.mappers;

import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.domain.dtos.request.AllUserDto;
import com.focados.foca.modules.users.domain.dtos.request.CreateUserDto;
import com.focados.foca.modules.users.domain.dtos.response.CreateUserResponseDto;
import com.focados.foca.modules.users.domain.dtos.response.UserResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public static UserModel mappingToUserEntity(CreateUserDto createDto) {
        UserModel user = new UserModel();
        user.setCpf(createDto.getCpf());
        user.setName(createDto.getName());
        user.setUsername(createDto.getUsername());
        user.setEmail(createDto.getEmail());
        user.setPhone(createDto.getPhone());
        user.setUserRegisterDate(LocalDateTime.now());
        return user;
    }

    public static CreateUserResponseDto mappingToUserResponse(UserModel user) {
        CreateUserResponseDto response = new CreateUserResponseDto();
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        //user.getId() and user.getUserRegisterDate() were intentionally omitted
        return response;
    }

    public static UserResponseDto mappingToUserListResponse(UserModel user) {
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setCpf(user.getCpf());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setUserRegisterDate(user.getUserRegisterDate());
        return response;
    }

    public static UserModel mappingToAllUserEntity(AllUserDto userDto) {
        UserModel user = new UserModel();
        user.setCpf(userDto.getCpf());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setUserRegisterDate(userDto.getUserRegisterDate());
        return user;
    }

    public static List<UserResponseDto> mappingToUserReponseList(List<UserModel> users) {
        return users.stream()
                .map(UserMapper::mappingToUserListResponse)
                .collect(Collectors.toList());
    }
}

