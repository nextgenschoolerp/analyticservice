package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UptimeSlaResponse {
    private UUID id;
    private String tenantId;
    private String periodMonthYear;
    private Integer totalMinutes;
    private Integer downtimeMinutes;
    private Double uptimePercentage;
    private Double slaTargetPct;
    private Boolean slaMet;
    private Instant createdAt;
}
