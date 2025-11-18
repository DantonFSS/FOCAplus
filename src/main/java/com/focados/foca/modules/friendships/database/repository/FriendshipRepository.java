package com.focados.foca.modules.friendships.database.repository;

import com.focados.foca.modules.friendships.database.entity.FriendshipModel;
import com.focados.foca.modules.friendships.database.entity.enums.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendshipRepository extends JpaRepository<FriendshipModel, UUID> {
    Optional<FriendshipModel> findByUserIdAndFriendId(UUID userId, UUID friendId);
    Optional<FriendshipModel> findByUserIdAndFriendIdOrFriendIdAndUserId(UUID userId, UUID friendId, UUID friendIdRev, UUID userIdRev);
    List<FriendshipModel> findAllByFriendIdAndStatus(UUID friendId, FriendshipStatus status);
    List<FriendshipModel> findAllByUserIdAndStatus(UUID userId, FriendshipStatus status);
    @Query("""
   SELECT f FROM FriendshipModel f
   WHERE (f.user.id = :userId OR f.friend.id = :userId) AND f.status = :status
""")
    List<FriendshipModel> findAcceptedFriendships(UUID userId, FriendshipStatus status);
}