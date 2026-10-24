package com.kvs.analytics.repo;

import com.kvs.analytics.entity.SystemHealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemHealthMetricRepository extends JpaRepository<SystemHealthMetric, UUID> {
    List<SystemHealthMetric> findByTenantIdOrderByRecordedAtDesc(String tenantId);
    Optional<SystemHealthMetric> findFirstByTenantIdOrderByRecordedAtDesc(String tenantId);
}
