package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateLimitPolicyRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "policyName cannot be blank")
    private String policyName;

    @NotBlank(message = "clientIdentifier cannot be blank")
    private String clientIdentifier;

    private Integer windowSeconds;
    private Integer maxRequests;
    private String enforcementAction;
}
