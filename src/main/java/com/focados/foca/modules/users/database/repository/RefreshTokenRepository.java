package com.focados.foca.modules.users.database.repository;

import com.focados.foca.modules.users.database.entity.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, UUID> {
    Optional<RefreshTokenModel> findByTokenHash(String tokenHash);
    Optional<RefreshTokenModel> findByUserId(UUID userId);
}

