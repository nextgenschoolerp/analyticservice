package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudBackupResponse {
    private UUID id;
    private UUID tenantId;
    private String snapshotName;
    private String cloudProvider;
    private String bucketLocation;
    private Long sizeBytes;
    private Instant pointInTime;
    private String status;
    private String checksumSha256;
    private Instant createdAt;
}
