package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_trustee_kpi_dashboards", indexes = {
        @Index(name = "idx_trustee_tenant", columnList = "tenant_id"),
        @Index(name = "idx_trustee_period", columnList = "period_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrusteeKpiDashboard extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "period_name", nullable = false, length = 100)
    private String periodName;

    @Column(name = "fee_collection_pct", nullable = false)
    @Builder.Default
    private Double feeCollectionPct = 95.5;

    @Column(name = "average_attendance_pct", nullable = false)
    @Builder.Default
    private Double averageAttendancePct = 94.2;

    @Column(name = "student_enrollment_count", nullable = false)
    @Builder.Default
    private Integer studentEnrollmentCount = 1250;

    @Column(name = "teacher_student_ratio", nullable = false)
    @Builder.Default
    private Double teacherStudentRatio = 18.5;

    @Column(name = "board_pass_percentage", nullable = false)
    @Builder.Default
    private Double boardPassPercentage = 98.6;

    @Column(name = "summary_notes", columnDefinition = "TEXT")
    private String summaryNotes;
}
