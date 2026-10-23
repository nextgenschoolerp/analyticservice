package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataModificationAuditRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "tableName is mandatory")
    private String tableName;

    @NotBlank(message = "operation is mandatory (INSERT, UPDATE, DELETE)")
    private String operation;

    @NotBlank(message = "recordId is mandatory")
    private String recordId;

    private String oldValuesJson;
    private String newValuesJson;
    private UUID modifiedByUserId;
    private String modifiedByUsername;
    private Long queryDurationMs;
    private String clientIp;
}
