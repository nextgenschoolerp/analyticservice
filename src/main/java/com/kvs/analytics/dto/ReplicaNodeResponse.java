package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplicaNodeResponse {
    private UUID id;
    private UUID tenantId;
    private String nodeHostname;
    private Integer port;
    private String replicaRole;
    private Long replicationLagMs;
    private Boolean isFailoverEligible;
    private Boolean isHealthy;
    private Instant lastHealthCheckAt;
    private Instant createdAt;
}
