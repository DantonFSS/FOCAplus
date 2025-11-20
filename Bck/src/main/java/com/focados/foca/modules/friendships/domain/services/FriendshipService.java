package com.focados.foca.modules.friendships.domain.services;

import com.focados.foca.modules.friendships.database.entity.FriendshipModel;
import com.focados.foca.modules.friendships.database.entity.enums.FriendshipStatus;
import com.focados.foca.modules.friendships.database.repository.FriendshipRepository;
import com.focados.foca.modules.friendships.domain.dtos.mappers.FriendshipMapper;
import com.focados.foca.modules.friendships.domain.dtos.response.FriendshipResponseDTO;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepo;
    private final UserRepository userRepo;

    // Solicitar amizade
    public void requestFriendship(UUID friendUserId) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        if (userId.equals(friendUserId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não pode ser amigo de si mesmo");

        // Checa se já existe amizade ou solicitação em qualquer direção
        boolean exists = friendshipRepo.findByUserIdAndFriendIdOrFriendIdAndUserId(userId, friendUserId, userId, friendUserId).isPresent();
        if (exists) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe amizade ou solicitação");

        var user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        var friend = userRepo.findById(friendUserId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Amigo não encontrado"));

        var f = new FriendshipModel();
        f.setUser(user);
        f.setFriend(friend);
        f.setStatus(FriendshipStatus.PENDING);
        friendshipRepo.save(f);
    }

    // Aceitar
    public void acceptFriendship(UUID friendshipId) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        var f = friendshipRepo.findById(friendshipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada"));
        if (!f.getFriend().getId().equals(userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Só o receptor pode aceitar");
        if (!f.getStatus().equals(FriendshipStatus.PENDING)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solicitação já processada");
        f.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepo.save(f);
    }

    // Recusar ou remover amizade
    public void rejectOrRemoveFriendship(UUID friendshipId) {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        var f = friendshipRepo.findById(friendshipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada"));
        // Pode remover se é solicitante ou receptor OU se status já accepted (unfriend)
        if (!f.getUser().getId().equals(userId) && !f.getFriend().getId().equals(userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão");
        friendshipRepo.delete(f);
    }

    public List<FriendshipResponseDTO> listReceivedRequests() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        return friendshipRepo.findAllByFriendIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream().map(FriendshipMapper::toResponse).toList();
    }

    public List<FriendshipResponseDTO> listSentRequests() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        return friendshipRepo.findAllByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream().map(FriendshipMapper::toResponse).toList();
    }

    public List<FriendshipResponseDTO> listAcceptedFriends() {
        UUID userId = AuthUtil.getAuthenticatedUserId();
        return friendshipRepo.findAcceptedFriendships(userId, FriendshipStatus.ACCEPTED)
                .stream().map(FriendshipMapper::toResponse).toList();
    }
}