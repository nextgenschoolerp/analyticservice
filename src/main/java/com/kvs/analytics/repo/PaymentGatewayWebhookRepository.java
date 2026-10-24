package com.kvs.analytics.repo;

import com.kvs.analytics.entity.PaymentGatewayWebhook;
import com.kvs.analytics.enums.WebhookClearanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentGatewayWebhookRepository extends JpaRepository<PaymentGatewayWebhook, UUID> {
    List<PaymentGatewayWebhook> findByTenantId(String tenantId);
    List<PaymentGatewayWebhook> findByTenantIdAndClearanceStatus(String tenantId, WebhookClearanceStatus status);
    Optional<PaymentGatewayWebhook> findByGatewayEventId(String eventId);
}
