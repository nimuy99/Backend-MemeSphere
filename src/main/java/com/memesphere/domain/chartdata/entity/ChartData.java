package com.memesphere.domain.chartdata.entity;

import com.memesphere.global.common.BaseEntity;
import com.memesphere.domain.memecoin.entity.MemeCoin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChartData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chart_id")
    private Long id;

    @Column
    private LocalDateTime recordedTime;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal priceChange;

    @Column
    private BigDecimal priceChangeRate;

    @Column
    private BigDecimal weighted_average_price;

    @Column
    private BigDecimal volume;

    @Column
    private BigDecimal low_price;

    @Column
    private BigDecimal high_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id", nullable = false) // 외래 키
    private MemeCoin memeCoin;

    public void setMemeCoin(MemeCoin memeCoin) {
        this.memeCoin = memeCoin;
        if (!memeCoin.getChartDataList().contains(this)) {
            memeCoin.getChartDataList().add(this);
        }
    }
}
