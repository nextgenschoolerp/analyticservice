package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudBackupRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "snapshotName is mandatory")
    private String snapshotName;

    private String cloudProvider; // AWS_S3, AZURE_BLOB, GCS
    private String bucketLocation;
}
