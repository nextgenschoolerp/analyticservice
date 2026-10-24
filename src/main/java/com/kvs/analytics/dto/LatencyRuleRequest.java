package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LatencyRuleRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "ruleName cannot be blank")
    private String ruleName;

    @NotBlank(message = "endpointPattern cannot be blank")
    private String endpointPattern;

    private Long thresholdMs;
    private Integer consecutiveBreaches;
    private String alertWebhookUrl;
}
