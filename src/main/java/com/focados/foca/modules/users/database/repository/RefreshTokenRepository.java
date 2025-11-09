package com.focados.foca.modules.users.database.repository;

import com.focados.foca.modules.users.database.entity.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, UUID> {
    Optional<RefreshTokenModel> findByTokenHash(String tokenHash);
    Optional<RefreshTokenModel> findByUserId(UUID userId);
    @Transactional
    @Modifying
    @Query("delete from RefreshTokenModel r where r.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}

