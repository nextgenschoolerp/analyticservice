package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppDispatchResponse {
    private String dispatchId;
    private String tenantId;
    private String recipientPhone;
    private String channelUsed; // WHATSAPP or SMS_FAILOVER
    private String messageStatus; // SENT, DELIVERED, FAILED
    private String carrierMessageId;
    private Instant dispatchedAt;
}
