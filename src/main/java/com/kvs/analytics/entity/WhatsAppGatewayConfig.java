package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "analytics_whatsapp_configs", indexes = {
        @Index(name = "idx_wa_tenant", columnList = "tenant_id"),
        @Index(name = "idx_wa_phone_id", columnList = "phone_number_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WhatsAppGatewayConfig extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "bsp_provider", nullable = false, length = 50)
    @Builder.Default
    private String bspProvider = "META_DIRECT";

    @Column(name = "phone_number_id", nullable = false, length = 100)
    private String phoneNumberId;

    @Column(name = "business_account_id", nullable = false, length = 100)
    private String businessAccountId;

    @Column(name = "access_token_encrypted", length = 500)
    private String accessTokenEncrypted;

    @Column(name = "sms_failover_enabled", nullable = false)
    @Builder.Default
    private Boolean smsFailoverEnabled = true;

    @Column(name = "sms_failover_gateway", length = 50)
    @Builder.Default
    private String smsFailoverGateway = "GUPSHUP_SMS";

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
