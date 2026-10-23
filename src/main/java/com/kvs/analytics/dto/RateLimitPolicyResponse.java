package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateLimitPolicyResponse {
    private UUID id;
    private UUID tenantId;
    private String policyName;
    private String clientIdentifier;
    private Integer windowSeconds;
    private Integer maxRequests;
    private String enforcementAction;
    private Boolean isActive;
    private Instant createdAt;
}
