package com.kvs.analytics.entity;

import com.kvs.analytics.enums.SyncBatchStatus;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_tally_sync_batches", indexes = {
        @Index(name = "idx_tally_tenant", columnList = "tenant_id"),
        @Index(name = "idx_tally_batch", columnList = "batch_number", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TallySyncBatch extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "batch_number", nullable = false, unique = true, length = 100)
    private String batchNumber;

    @Column(name = "sync_date", nullable = false)
    private Instant syncDate;

    @Column(name = "voucher_count", nullable = false)
    @Builder.Default
    private Integer voucherCount = 0;

    @Column(name = "total_debit", precision = 14, scale = 2)
    private BigDecimal totalDebit;

    @Column(name = "total_credit", precision = 14, scale = 2)
    private BigDecimal totalCredit;

    @Column(name = "xml_payload", columnDefinition = "TEXT")
    private String xmlPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private SyncBatchStatus status = SyncBatchStatus.COMPLETED;

    @Column(name = "error_message", length = 500)
    private String errorMessage;
}
