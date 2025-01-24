package com.memesphere.user.domain;

import com.memesphere.chat.domain.Chat;
import com.memesphere.chat.domain.ChatLike;
import com.memesphere.collection.domain.Collection;
import com.memesphere.notification.domain.Notification;
import com.memesphere.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;

    @Column(nullable = false, unique = true)
    private Long loginId;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Setter
    @Column
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column
    private String wallet;

    @Setter
    private String accessToken;

    @Setter
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Collection> collectionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatLike> chatLikeList = new ArrayList<>();
}
