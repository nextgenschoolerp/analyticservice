package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHealthMetricResponse {
    private UUID id;
    private String tenantId;
    private String serverNodeId;
    private Double cpuUsagePct;
    private Double memoryUsagePct;
    private Integer activeDbConnections;
    private Integer idleDbConnections;
    private Double diskUsagePct;
    private Long avgResponseLatencyMs;
    private Instant recordedAt;
}
