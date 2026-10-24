package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_custom_reports", indexes = {
        @Index(name = "idx_report_tenant", columnList = "tenant_id"),
        @Index(name = "idx_report_category", columnList = "category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomReportDefinition extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "report_name", nullable = false, length = 150)
    private String reportName;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "dimensions_json", columnDefinition = "TEXT")
    private String dimensionsJson;

    @Column(name = "metrics_json", columnDefinition = "TEXT")
    private String metricsJson;

    @Column(name = "filters_json", columnDefinition = "TEXT")
    private String filtersJson;

    @Column(name = "chart_type", length = 30)
    @Builder.Default
    private String chartType = "BAR";

    @Column(name = "created_by_user_id", nullable = false)
    private UUID createdByUserId;
}
