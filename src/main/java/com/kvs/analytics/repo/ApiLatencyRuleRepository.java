package com.kvs.analytics.repo;

import com.kvs.analytics.entity.ApiLatencyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiLatencyRuleRepository extends JpaRepository<ApiLatencyRule, UUID> {
    List<ApiLatencyRule> findByTenantId(String tenantId);
    List<ApiLatencyRule> findByTenantIdAndIsActiveTrue(String tenantId);
}
