package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyResponse {
    private UUID id;
    private String tenantId;
    private String keyName;
    private String clientId;
    private String apiKeyPrefix;
    private String rawSecretToken; // Only populated on initial creation
    private String allowedScopes;
    private String rateLimitTier;
    private Instant expiresAt;
    private Boolean isRevoked;
    private Instant createdAt;
}
