package com.memesphere.domain.memecoin.entity;

import com.memesphere.domain.chat.entity.Chat;
import com.memesphere.domain.collection.entity.Collection;
import com.memesphere.domain.chartdata.entity.ChartData;
import com.memesphere.global.common.BaseEntity;
import com.memesphere.domain.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemeCoin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coin_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String symbol;

    @Column
    private String image;

    @Column
    private String description;

    @Column
    private boolean collectionActive;

    @Column
    private Integer trendRank;

    @ElementCollection
    @CollectionTable(name = "CoinKeywords", joinColumns = @JoinColumn(name = "coin_id"))
    @Column(name = "keyword")
    @Builder.Default
    private List<String> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Collection> collectionList = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("recordedTime DESC")
    @Builder.Default
    private List<ChartData> chartDataList = new ArrayList<>();

    @Transactional
    public void updateRank(Integer trendRank) {
        this.trendRank = trendRank;
    }
}
