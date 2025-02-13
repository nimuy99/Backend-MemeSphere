package com.memesphere.domain.notification.entity;

import com.memesphere.domain.memecoin.entity.MemeCoin;
import com.memesphere.global.common.BaseEntity;
import com.memesphere.domain.user.entity.User;
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
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_id")
    private Long id;

    @Column(nullable = false)
    private Integer volatility;  // 변동성

    @Column(nullable = false)
    private Integer stTime;  // 기준 시간

    @Column(nullable = false)
    private Boolean isRising;  // 상승 또는 하락

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isOn;  // 알람 on/off

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="coin_id")
    private MemeCoin memeCoin;

    public void updateIsOn(Boolean isOn) {
        this.isOn = isOn;
    }
}
