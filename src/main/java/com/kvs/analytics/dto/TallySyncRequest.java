package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TallySyncRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    private Instant fromDate;
    private Instant toDate;
    private String voucherType; // RECEIPT, PAYMENT, JOURNAL
}
