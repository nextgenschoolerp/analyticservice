package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomReportResponse {
    private UUID id;
    private String tenantId;
    private String reportName;
    private String category;
    private String dimensionsJson;
    private String metricsJson;
    private String filtersJson;
    private String chartType;
    private UUID createdByUserId;
    private Instant createdAt;
}
