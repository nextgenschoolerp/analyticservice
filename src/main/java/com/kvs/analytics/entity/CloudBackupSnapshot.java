package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_cloud_backup_snapshots", indexes = {
        @Index(name = "idx_backup_tenant", columnList = "tenant_id"),
        @Index(name = "idx_backup_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudBackupSnapshot extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "snapshot_name", nullable = false, length = 150)
    private String snapshotName;

    @Column(name = "cloud_provider", nullable = false, length = 50)
    @Builder.Default
    private String cloudProvider = "AWS_S3";

    @Column(name = "bucket_location", nullable = false, length = 250)
    private String bucketLocation;

    @Column(name = "size_bytes", nullable = false)
    @Builder.Default
    private Long sizeBytes = 0L;

    @Column(name = "point_in_time", nullable = false)
    private Instant pointInTime;

    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private String status = "COMPLETED";

    @Column(name = "checksum_sha256", length = 100)
    private String checksumSha256;
}
