package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnonymizationResponse {
    private UUID id;
    private String tenantId;
    private String jobName;
    private String targetStagingDb;
    private Integer tablesMasked;
    private Long recordsMasked;
    private String rulesAppliedJson;
    private Integer durationSeconds;
    private String status;
    private Instant createdAt;
}
