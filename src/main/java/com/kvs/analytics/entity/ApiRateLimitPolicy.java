package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_rate_limit_policies", indexes = {
        @Index(name = "idx_ratelimit_tenant", columnList = "tenant_id"),
        @Index(name = "idx_ratelimit_identifier", columnList = "client_identifier")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRateLimitPolicy extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "policy_name", nullable = false, length = 100)
    private String policyName;

    @Column(name = "client_identifier", nullable = false, length = 100)
    private String clientIdentifier;

    @Column(name = "window_seconds", nullable = false)
    @Builder.Default
    private Integer windowSeconds = 60;

    @Column(name = "max_requests", nullable = false)
    @Builder.Default
    private Integer maxRequests = 100;

    @Column(name = "enforcement_action", nullable = false, length = 20)
    @Builder.Default
    private String enforcementAction = "THROTTLE";

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
