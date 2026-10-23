package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "gatewayProvider is mandatory")
    private String gatewayProvider;

    @NotBlank(message = "gatewayEventId is mandatory")
    private String gatewayEventId;

    @NotBlank(message = "eventType is mandatory")
    private String eventType;

    private String transactionReference;
    private BigDecimal amount;
    private String currency;
    private String payloadJson;
    private String signatureHeader;
}
