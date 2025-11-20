package com.focados.foca.modules.friendships.database.entity;

import com.focados.foca.modules.friendships.database.entity.enums.FriendshipStatus;
import com.focados.foca.modules.users.database.entity.UserModel;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "friendships", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_user_id"}))
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "friend_user_id", nullable = false)
    private UserModel friend;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status = FriendshipStatus.PENDING;

    @Column(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now();
}