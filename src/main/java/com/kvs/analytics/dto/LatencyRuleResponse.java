package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LatencyRuleResponse {
    private UUID id;
    private UUID tenantId;
    private String ruleName;
    private String endpointPattern;
    private Long thresholdMs;
    private Integer consecutiveBreaches;
    private String alertWebhookUrl;
    private Boolean isActive;
    private Instant createdAt;
}
