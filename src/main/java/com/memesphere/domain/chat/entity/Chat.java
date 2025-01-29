package com.memesphere.domain.chat.entity;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.global.common.BaseEntity;
import com.memesphere.domain.user.entity.User;
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
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_id")
    private Long id;

    @Column
    private String message;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="coin_id")
    private MemeCoin memeCoin;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatLike> chatLikeList = new ArrayList<>();
}
