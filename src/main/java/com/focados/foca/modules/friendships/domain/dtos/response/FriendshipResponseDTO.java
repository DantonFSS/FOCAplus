package com.focados.foca.modules.friendships.domain.dtos.response;

import com.focados.foca.modules.friendships.database.entity.enums.FriendshipStatus;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class FriendshipResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID friendUserId;
    private FriendshipStatus status;
    private ZonedDateTime createdAt;
}