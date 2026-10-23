package com.kvs.analytics.dto;

import com.kvs.analytics.enums.SyncBatchStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TallySyncResponse {
    private UUID id;
    private UUID tenantId;
    private String batchNumber;
    private Instant syncDate;
    private Integer voucherCount;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private String xmlPayload;
    private SyncBatchStatus status;
    private Instant createdAt;
}
