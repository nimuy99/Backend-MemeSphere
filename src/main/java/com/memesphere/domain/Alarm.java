package com.memesphere.domain;

import com.memesphere.domain.common.BaseEntity;
import com.memesphere.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coin_id")
    private MemeCoin memeCoin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;  // 상승 또는 하락

    @Column
    private Double volatility;  // 변동성

    @Column
    private Integer duration;  // 기준시간
}
