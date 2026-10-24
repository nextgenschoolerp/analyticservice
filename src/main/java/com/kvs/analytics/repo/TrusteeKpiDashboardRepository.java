package com.kvs.analytics.repo;

import com.kvs.analytics.entity.TrusteeKpiDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrusteeKpiDashboardRepository extends JpaRepository<TrusteeKpiDashboard, UUID> {
    List<TrusteeKpiDashboard> findByTenantIdOrderByCreatedAtDesc(String tenantId);
    Optional<TrusteeKpiDashboard> findByTenantIdAndPeriodName(String tenantId, String periodName);
}
