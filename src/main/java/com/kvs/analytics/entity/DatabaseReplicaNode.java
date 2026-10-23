package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_database_replicas", indexes = {
        @Index(name = "idx_replica_tenant", columnList = "tenant_id"),
        @Index(name = "idx_replica_host", columnList = "node_hostname")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatabaseReplicaNode extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "node_hostname", nullable = false, length = 150)
    private String nodeHostname;

    @Column(name = "port", nullable = false)
    @Builder.Default
    private Integer port = 5432;

    @Column(name = "replica_role", nullable = false, length = 30)
    @Builder.Default
    private String replicaRole = "STANDBY";

    @Column(name = "replication_lag_ms", nullable = false)
    @Builder.Default
    private Long replicationLagMs = 0L;

    @Column(name = "is_failover_eligible", nullable = false)
    @Builder.Default
    private Boolean isFailoverEligible = true;

    @Column(name = "is_healthy", nullable = false)
    @Builder.Default
    private Boolean isHealthy = true;

    @Column(name = "last_health_check_at")
    private Instant lastHealthCheckAt;
}
