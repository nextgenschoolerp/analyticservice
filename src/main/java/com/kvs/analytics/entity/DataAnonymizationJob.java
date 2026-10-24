package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_anonymization_jobs", indexes = {
        @Index(name = "idx_anon_tenant", columnList = "tenant_id"),
        @Index(name = "idx_anon_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataAnonymizationJob extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "job_name", nullable = false, length = 150)
    private String jobName;

    @Column(name = "target_staging_db", nullable = false, length = 150)
    private String targetStagingDb;

    @Column(name = "tables_masked", nullable = false)
    @Builder.Default
    private Integer tablesMasked = 0;

    @Column(name = "records_masked", nullable = false)
    @Builder.Default
    private Long recordsMasked = 0L;

    @Column(name = "rules_applied_json", columnDefinition = "TEXT")
    private String rulesAppliedJson;

    @Column(name = "duration_seconds", nullable = false)
    @Builder.Default
    private Integer durationSeconds = 0;

    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private String status = "COMPLETED";
}
