package com.kvs.analytics.dto;

import com.kvs.analytics.enums.IncidentSeverity;
import com.kvs.analytics.enums.IncidentStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusIncidentResponse {
    private UUID id;
    private String tenantId;
    private String incidentTitle;
    private IncidentSeverity severity;
    private IncidentStatus status;
    private String impactedServices;
    private String incidentMessage;
    private Boolean isPublic;
    private Instant createdAt;
    private Instant updatedAt;
}
