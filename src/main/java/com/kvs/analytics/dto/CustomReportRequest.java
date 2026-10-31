package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomReportRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "reportName is mandatory")
    private String reportName;

    @NotBlank(message = "category is mandatory (ACADEMIC, FINANCE, ATTENDANCE, STAFF_HR)")
    private String category;

    private String dimensionsJson;
    private String metricsJson;
    private String filtersJson;
    private String chartType;
    private String createdByUserId;
}
