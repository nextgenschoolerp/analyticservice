package com.kvs.analytics.entity;

import com.kvs.analytics.enums.WebhookClearanceStatus;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "analytics_payment_webhooks", indexes = {
        @Index(name = "idx_webhook_tenant", columnList = "tenant_id"),
        @Index(name = "idx_webhook_event_id", columnList = "gateway_event_id"),
        @Index(name = "idx_webhook_status", columnList = "clearance_status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentGatewayWebhook extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "gateway_provider", nullable = false, length = 50)
    private String gatewayProvider;

    @Column(name = "gateway_event_id", nullable = false, length = 100)
    private String gatewayEventId;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "transaction_reference", length = 100)
    private String transactionReference;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10)
    @Builder.Default
    private String currency = "INR";

    @Column(name = "payload_json", columnDefinition = "TEXT")
    private String payloadJson;

    @Column(name = "signature_header", length = 250)
    private String signatureHeader;

    @Enumerated(EnumType.STRING)
    @Column(name = "clearance_status", nullable = false, length = 30)
    @Builder.Default
    private WebhookClearanceStatus clearanceStatus = WebhookClearanceStatus.PENDING;

    @Column(name = "clearance_notes", length = 500)
    private String clearanceNotes;
}
