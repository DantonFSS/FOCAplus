package com.focados.foca.modules.users.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cpf;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String passwordHash;
    @Column(unique = true, nullable = false)
    private String username;

    @CreatedDate
    private LocalDateTime userRegisterDate;
}

