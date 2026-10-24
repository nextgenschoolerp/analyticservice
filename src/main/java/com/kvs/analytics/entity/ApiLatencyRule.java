package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_latency_rules", indexes = {
        @Index(name = "idx_latency_tenant", columnList = "tenant_id"),
        @Index(name = "idx_latency_pattern", columnList = "endpoint_pattern")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiLatencyRule extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "rule_name", nullable = false, length = 150)
    private String ruleName;

    @Column(name = "endpoint_pattern", nullable = false, length = 250)
    private String endpointPattern;

    @Column(name = "threshold_ms", nullable = false)
    @Builder.Default
    private Long thresholdMs = 500L;

    @Column(name = "consecutive_breaches", nullable = false)
    @Builder.Default
    private Integer consecutiveBreaches = 3;

    @Column(name = "alert_webhook_url", length = 500)
    private String alertWebhookUrl;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
