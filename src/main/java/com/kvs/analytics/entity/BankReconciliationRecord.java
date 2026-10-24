package com.kvs.analytics.entity;

import com.kvs.analytics.enums.ReconciliationStatus;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_bank_reconciliation", indexes = {
        @Index(name = "idx_recon_tenant", columnList = "tenant_id"),
        @Index(name = "idx_recon_status", columnList = "reconciliation_status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankReconciliationRecord extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "statement_transaction_id", nullable = false, length = 100)
    private String statementTransactionId;

    @Column(name = "erp_transaction_ref", length = 100)
    private String erpTransactionRef;

    @Column(name = "bank_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal bankAmount;

    @Column(name = "erp_ledger_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal erpLedgerAmount;

    @Column(name = "difference_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal differenceAmount;

    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "reconciliation_status", nullable = false, length = 30)
    @Builder.Default
    private ReconciliationStatus reconciliationStatus = ReconciliationStatus.MATCHED;

    @Column(name = "resolution_notes", length = 500)
    private String resolutionNotes;
}
