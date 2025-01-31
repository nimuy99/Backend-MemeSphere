package com.memesphere.domain.user.entity;

import com.memesphere.domain.chat.entity.Chat;
import com.memesphere.domain.chat.entity.ChatLike;
import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.notification.entity.Notification;
import com.memesphere.global.common.BaseEntity;
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
    private String password;

    @Column(length = 8)
    private String birth;

    private String profileImage;

    private String wallet;

    @Setter
    private String accessToken;

    @Setter
    private String refreshToken;


    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Collection> collectionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatLike> chatLikeList = new ArrayList<>();
}
