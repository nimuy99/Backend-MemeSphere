package com.memesphere.domain.memecoin.domain;

import com.memesphere.chat.domain.Chat;
import com.memesphere.domain.collection.domain.Collection;
import com.memesphere.domain.chartdata.domain.ChartData;
import com.memesphere.global.domain.BaseEntity;
import com.memesphere.domain.notification.domain.Notification;
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

    @ElementCollection
    @CollectionTable(name = "CoinKeywords", joinColumns = @JoinColumn(name = "coin_id"))
    @Column(name = "keyword")
    private List<String> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    private List<Collection> collectionList = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "memeCoin", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("recordedTime DESC")
    private List<ChartData> chartDataList = new ArrayList<>();
}
