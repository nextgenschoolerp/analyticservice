package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportExecutionResult {
    private UUID reportId;
    private String reportName;
    private String category;
    private Integer rowCount;
    private List<String> columnHeaders;
    private List<Map<String, Object>> rows;
    private Instant executedAt;
    private Long executionDurationMs;
}
