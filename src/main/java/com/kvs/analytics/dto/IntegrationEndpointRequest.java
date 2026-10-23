package com.kvs.analytics.dto;

import com.kvs.analytics.enums.IntegrationAuthType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationEndpointRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "endpointName cannot be blank")
    private String endpointName;

    @NotBlank(message = "targetUrl cannot be blank")
    private String targetUrl;

    @NotBlank(message = "httpMethod cannot be blank")
    private String httpMethod;

    @NotNull(message = "authType is mandatory")
    private IntegrationAuthType authType;

    private String headersJson;
    private String description;
}
