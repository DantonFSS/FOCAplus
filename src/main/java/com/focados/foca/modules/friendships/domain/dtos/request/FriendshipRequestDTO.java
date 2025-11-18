package com.focados.foca.modules.friendships.domain.dtos.request;

import lombok.Data;
import java.util.UUID;

@Data
public class FriendshipRequestDTO {
    private UUID friendUserId;
}