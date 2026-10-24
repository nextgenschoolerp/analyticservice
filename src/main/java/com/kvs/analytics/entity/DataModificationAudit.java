package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_data_modification_audits", indexes = {
        @Index(name = "idx_modaudit_tenant", columnList = "tenant_id"),
        @Index(name = "idx_modaudit_table_rec", columnList = "table_name, record_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataModificationAudit extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    @Column(name = "operation", nullable = false, length = 20)
    private String operation;

    @Column(name = "record_id", nullable = false, length = 100)
    private String recordId;

    @Column(name = "old_values_json", columnDefinition = "TEXT")
    private String oldValuesJson;

    @Column(name = "new_values_json", columnDefinition = "TEXT")
    private String newValuesJson;

    @Column(name = "modified_by_user_id", nullable = false)
    private UUID modifiedByUserId;

    @Column(name = "modified_by_username", length = 150)
    private String modifiedByUsername;

    @Column(name = "query_duration_ms")
    private Long queryDurationMs;

    @Column(name = "client_ip", length = 50)
    private String clientIp;
}
