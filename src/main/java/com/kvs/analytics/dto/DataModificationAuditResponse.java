package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataModificationAuditResponse {
    private UUID id;
    private String tenantId;
    private String tableName;
    private String operation;
    private String recordId;
    private String oldValuesJson;
    private String newValuesJson;
    private String modifiedByUserId;
    private String modifiedByUsername;
    private Long queryDurationMs;
    private String clientIp;
    private Instant createdAt;
}
