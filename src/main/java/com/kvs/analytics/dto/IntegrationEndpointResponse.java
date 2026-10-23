package com.kvs.analytics.dto;

import com.kvs.analytics.enums.IntegrationAuthType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationEndpointResponse {
    private UUID id;
    private UUID tenantId;
    private String endpointName;
    private String targetUrl;
    private String httpMethod;
    private IntegrationAuthType authType;
    private String headersJson;
    private Boolean isActive;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
