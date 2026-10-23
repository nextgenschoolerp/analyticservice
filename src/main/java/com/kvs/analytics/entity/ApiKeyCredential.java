package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_api_keys", indexes = {
        @Index(name = "idx_apikey_tenant", columnList = "tenant_id"),
        @Index(name = "idx_apikey_client_id", columnList = "client_id", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKeyCredential extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "key_name", nullable = false, length = 100)
    private String keyName;

    @Column(name = "client_id", nullable = false, unique = true, length = 64)
    private String clientId;

    @Column(name = "api_key_prefix", nullable = false, length = 16)
    private String apiKeyPrefix;

    @Column(name = "secret_hash", nullable = false, length = 128)
    private String secretHash;

    @Column(name = "allowed_scopes", length = 500)
    private String allowedScopes;

    @Column(name = "rate_limit_tier", length = 50)
    @Builder.Default
    private String rateLimitTier = "STANDARD";

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "is_revoked", nullable = false)
    @Builder.Default
    private Boolean isRevoked = false;
}
