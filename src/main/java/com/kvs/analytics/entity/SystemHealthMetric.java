package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_system_health_metrics", indexes = {
        @Index(name = "idx_health_tenant", columnList = "tenant_id"),
        @Index(name = "idx_health_recorded", columnList = "recorded_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemHealthMetric extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "server_node_id", nullable = false, length = 100)
    private String serverNodeId;

    @Column(name = "cpu_usage_pct", nullable = false)
    private Double cpuUsagePct;

    @Column(name = "memory_usage_pct", nullable = false)
    private Double memoryUsagePct;

    @Column(name = "active_db_connections", nullable = false)
    private Integer activeDbConnections;

    @Column(name = "idle_db_connections", nullable = false)
    private Integer idleDbConnections;

    @Column(name = "disk_usage_pct")
    private Double diskUsagePct;

    @Column(name = "avg_response_latency_ms")
    private Long avgResponseLatencyMs;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;
}
