package com.kvs.analytics.entity;

import com.kvs.analytics.enums.IncidentSeverity;
import com.kvs.analytics.enums.IncidentStatus;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_status_incidents", indexes = {
        @Index(name = "idx_incident_tenant", columnList = "tenant_id"),
        @Index(name = "idx_incident_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusIncidentNotice extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "incident_title", nullable = false, length = 200)
    private String incidentTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 20)
    @Builder.Default
    private IncidentSeverity severity = IncidentSeverity.MINOR;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private IncidentStatus status = IncidentStatus.INVESTIGATING;

    @Column(name = "impacted_services", length = 250)
    private String impactedServices;

    @Column(name = "incident_message", columnDefinition = "TEXT")
    private String incidentMessage;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = true;
}
