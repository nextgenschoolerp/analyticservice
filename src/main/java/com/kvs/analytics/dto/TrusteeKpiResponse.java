package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrusteeKpiResponse {
    private UUID id;
    private UUID tenantId;
    private String periodName;
    private Double feeCollectionPct;
    private Double averageAttendancePct;
    private Integer studentEnrollmentCount;
    private Double teacherStudentRatio;
    private Double boardPassPercentage;
    private String summaryNotes;
    private Instant createdAt;
}
