package com.memesphere.domain;

import com.memesphere.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
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

    @OneToOne(mappedBy = "memeCoin", cascade = CascadeType.ALL)
    private ChartData chartData;
}
