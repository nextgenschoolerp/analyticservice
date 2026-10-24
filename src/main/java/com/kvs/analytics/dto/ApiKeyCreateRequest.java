package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyCreateRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "keyName is mandatory")
    private String keyName;

    private String allowedScopes;
    private String rateLimitTier;
    private Instant expiresAt;
}
