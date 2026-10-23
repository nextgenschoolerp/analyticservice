package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrusteeKpiRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "periodName is mandatory (e.g. FY 2026-27 Q2)")
    private String periodName;

    private Double feeCollectionPct;
    private Double averageAttendancePct;
    private Integer studentEnrollmentCount;
    private Double teacherStudentRatio;
    private Double boardPassPercentage;
    private String summaryNotes;
}
