package com.memesphere.domain.collection.entity;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.global.domain.BaseEntity;
import com.memesphere.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Collection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="collect_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="coin_id")
    private MemeCoin memeCoin;

    public Long getMemeCoinId() {
        return memeCoin.getId();
    }
}
