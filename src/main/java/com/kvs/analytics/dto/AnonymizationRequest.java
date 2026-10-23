package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnonymizationRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "jobName is mandatory")
    private String jobName;

    @NotBlank(message = "targetStagingDb is mandatory")
    private String targetStagingDb;

    private String rulesAppliedJson;
}
