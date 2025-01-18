package com.memesphere.domain;

import com.memesphere.domain.common.BaseEntity;
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
    private LocalDateTime recorded_time;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal price_change;

    @Column
    private BigDecimal market_cap;

    @Column
    private Integer volume;

    @Column
    private BigDecimal low_price;

    @Column
    private BigDecimal high_price;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="coin_id")
    private MemeCoin memeCoin;
}
