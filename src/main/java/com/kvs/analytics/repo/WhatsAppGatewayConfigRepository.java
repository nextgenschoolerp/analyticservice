package com.kvs.analytics.repo;

import com.kvs.analytics.entity.WhatsAppGatewayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WhatsAppGatewayConfigRepository extends JpaRepository<WhatsAppGatewayConfig, UUID> {
    List<WhatsAppGatewayConfig> findByTenantId(UUID tenantId);
    Optional<WhatsAppGatewayConfig> findByTenantIdAndIsActiveTrue(UUID tenantId);
}
