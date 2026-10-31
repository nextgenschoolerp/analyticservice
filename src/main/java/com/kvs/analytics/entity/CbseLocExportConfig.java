package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_cbse_loc_configs", indexes = {
        @Index(name = "idx_cbseloc_tenant", columnList = "tenant_id"),
        @Index(name = "idx_cbseloc_year_class", columnList = "academic_year_id, class_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CbseLocExportConfig extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "academic_year_id", nullable = false)
    private UUID academicYearId;

    @Column(name = "class_name", nullable = false, length = 50)
    private String className;

    @Column(name = "candidate_count", nullable = false)
    @Builder.Default
    private Integer candidateCount = 0;

    @Column(name = "export_format", nullable = false, length = 50)
    @Builder.Default
    private String exportFormat = "CBSE_TEXT_FORMAT";

    @Column(name = "generated_file_url", length = 500)
    private String generatedFileUrl;

    @Column(name = "generated_by_user_id", nullable = false)
    private String generatedByUserId;
}
