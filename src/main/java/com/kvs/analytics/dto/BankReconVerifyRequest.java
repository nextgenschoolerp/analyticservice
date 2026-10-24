package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankReconVerifyRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "statementTransactionId is mandatory")
    private String statementTransactionId;

    private String erpTransactionRef;

    @NotNull(message = "bankAmount is mandatory")
    private BigDecimal bankAmount;

    @NotNull(message = "erpLedgerAmount is mandatory")
    private BigDecimal erpLedgerAmount;

    private Instant transactionDate;
}
