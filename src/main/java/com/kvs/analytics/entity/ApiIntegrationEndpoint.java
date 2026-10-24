package com.kvs.analytics.entity;

import com.kvs.analytics.enums.IntegrationAuthType;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_integration_endpoints", indexes = {
        @Index(name = "idx_endpoint_tenant", columnList = "tenant_id"),
        @Index(name = "idx_endpoint_active", columnList = "is_active")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiIntegrationEndpoint extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "endpoint_name", nullable = false, length = 150)
    private String endpointName;

    @Column(name = "target_url", nullable = false, length = 500)
    private String targetUrl;

    @Column(name = "http_method", nullable = false, length = 10)
    private String httpMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type", nullable = false, length = 30)
    private IntegrationAuthType authType;

    @Column(name = "headers_json", columnDefinition = "TEXT")
    private String headersJson;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "description", length = 500)
    private String description;
}
