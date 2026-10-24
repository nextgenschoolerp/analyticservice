package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_system_uptime_sla", indexes = {
        @Index(name = "idx_sla_tenant", columnList = "tenant_id"),
        @Index(name = "idx_sla_period", columnList = "period_month_year")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemUptimeSla extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "period_month_year", nullable = false, length = 20)
    private String periodMonthYear;

    @Column(name = "total_minutes", nullable = false)
    @Builder.Default
    private Integer totalMinutes = 43200;

    @Column(name = "downtime_minutes", nullable = false)
    @Builder.Default
    private Integer downtimeMinutes = 0;

    @Column(name = "uptime_percentage", nullable = false)
    @Builder.Default
    private Double uptimePercentage = 100.0;

    @Column(name = "sla_target_pct", nullable = false)
    @Builder.Default
    private Double slaTargetPct = 99.9;

    @Column(name = "sla_met", nullable = false)
    @Builder.Default
    private Boolean slaMet = true;
}
