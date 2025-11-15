package com.focados.foca.modules.users.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserResponseDto {
    //add this to retrieve user_id in response
    //private UUID id;
    private String name;
    private String username;
    private String email;
    private String phone;
    //@CreatedDate
    //private LocalDateTime userRegisterDate;
}

