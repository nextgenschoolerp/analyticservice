package com.kvs.analytics.dto;

import com.kvs.analytics.enums.WebhookClearanceStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookResponse {
    private UUID id;
    private String tenantId;
    private String gatewayProvider;
    private String gatewayEventId;
    private String eventType;
    private String transactionReference;
    private BigDecimal amount;
    private String currency;
    private WebhookClearanceStatus clearanceStatus;
    private String clearanceNotes;
    private Instant createdAt;
}
