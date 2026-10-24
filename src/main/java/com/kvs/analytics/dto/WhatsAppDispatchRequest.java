package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppDispatchRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "recipientPhone is mandatory")
    private String recipientPhone;

    @NotBlank(message = "templateName is mandatory")
    private String templateName;

    @NotBlank(message = "messageContent is mandatory")
    private String messageContent;

    private Boolean enableSmsFailover;
}
