package com.focados.foca.modules.users.database.repository;

import com.focados.foca.modules.users.database.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByCpf(String cpf);
    Optional<UserModel> findByUsername(String username);
}

