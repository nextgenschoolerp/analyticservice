package com.kvs.analytics.repo;

import com.kvs.analytics.entity.ApiIntegrationEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiIntegrationEndpointRepository extends JpaRepository<ApiIntegrationEndpoint, UUID> {
    List<ApiIntegrationEndpoint> findByTenantId(UUID tenantId);
    List<ApiIntegrationEndpoint> findByTenantIdAndIsActiveTrue(UUID tenantId);
}
