package com.kvs.analytics.repo;

import com.kvs.analytics.entity.ApiRateLimitPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiRateLimitPolicyRepository extends JpaRepository<ApiRateLimitPolicy, UUID> {
    List<ApiRateLimitPolicy> findByTenantId(String tenantId);
    Optional<ApiRateLimitPolicy> findByTenantIdAndClientIdentifier(String tenantId, String clientIdentifier);
}
