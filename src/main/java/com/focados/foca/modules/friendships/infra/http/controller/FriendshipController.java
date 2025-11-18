package com.focados.foca.modules.friendships.infra.http.controller;

import com.focados.foca.modules.friendships.domain.dtos.request.FriendshipRequestDTO;
import com.focados.foca.modules.friendships.domain.dtos.response.FriendshipResponseDTO;
import com.focados.foca.modules.friendships.domain.services.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService service;

    // Solicitar amizade
    @PostMapping
    public ResponseEntity<Void> request(@RequestBody FriendshipRequestDTO dto) {
        service.requestFriendship(dto.getFriendUserId());
        return ResponseEntity.noContent().build();
    }

    // Aceitar amizade
    @PutMapping("/accept/{friendshipId}")
    public ResponseEntity<Void> accept(@PathVariable UUID friendshipId) {
        service.acceptFriendship(friendshipId);
        return ResponseEntity.noContent().build();
    }

    // Remover/rejeitar amizade
    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<Void> remove(@PathVariable UUID friendshipId) {
        service.rejectOrRemoveFriendship(friendshipId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/received")
    public ResponseEntity<List<FriendshipResponseDTO>> received() {
        return ResponseEntity.ok(service.listReceivedRequests());
    }

    @GetMapping("/sent")
    public ResponseEntity<List<FriendshipResponseDTO>> sent() {
        return ResponseEntity.ok(service.listSentRequests());
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<FriendshipResponseDTO>> accepted() {
        return ResponseEntity.ok(service.listAcceptedFriends());
    }
}