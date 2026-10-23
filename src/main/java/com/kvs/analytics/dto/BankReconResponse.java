package com.kvs.analytics.dto;

import com.kvs.analytics.enums.ReconciliationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankReconResponse {
    private UUID id;
    private UUID tenantId;
    private String statementTransactionId;
    private String erpTransactionRef;
    private BigDecimal bankAmount;
    private BigDecimal erpLedgerAmount;
    private BigDecimal differenceAmount;
    private Instant transactionDate;
    private ReconciliationStatus reconciliationStatus;
    private String resolutionNotes;
    private Instant createdAt;
}
