package com.focados.foca.modules.friendships.domain.dtos.mappers;

import com.focados.foca.modules.friendships.database.entity.FriendshipModel;
import com.focados.foca.modules.friendships.domain.dtos.response.FriendshipResponseDTO;

public class FriendshipMapper {
    public static FriendshipResponseDTO toResponse(FriendshipModel model) {
        FriendshipResponseDTO dto = new FriendshipResponseDTO();
        dto.setId(model.getId());
        dto.setUserId(model.getUser().getId());
        dto.setFriendUserId(model.getFriend().getId());
        dto.setStatus(model.getStatus());
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }
}