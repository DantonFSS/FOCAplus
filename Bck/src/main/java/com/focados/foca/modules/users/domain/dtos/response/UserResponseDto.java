package com.focados.foca.modules.users.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {
    private UUID id;
    private String cpf;
    private String name;
    private String username;
    private String email;
    private String phone;

    @CreatedDate
    private LocalDateTime userRegisterDate;
}

