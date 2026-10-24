package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemHealthMetricRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "serverNodeId is mandatory")
    private String serverNodeId;

    @NotNull(message = "cpuUsagePct is mandatory")
    private Double cpuUsagePct;

    @NotNull(message = "memoryUsagePct is mandatory")
    private Double memoryUsagePct;

    @NotNull(message = "activeDbConnections is mandatory")
    private Integer activeDbConnections;

    private Integer idleDbConnections;
    private Double diskUsagePct;
    private Long avgResponseLatencyMs;
}
