package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CbseLocExportRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotNull(message = "academicYearId is mandatory")
    private UUID academicYearId;

    @NotBlank(message = "className is mandatory (e.g. Class 10 or Class 12)")
    private String className;

    private String exportFormat;
    private UUID generatedByUserId;
}
