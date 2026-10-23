package com.kvs.analytics.dto;

import com.kvs.analytics.enums.IncidentSeverity;
import com.kvs.analytics.enums.IncidentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusIncidentRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "incidentTitle is mandatory")
    private String incidentTitle;

    private IncidentSeverity severity;
    private IncidentStatus status;
    private String impactedServices;
    private String incidentMessage;
    private Boolean isPublic;
}
