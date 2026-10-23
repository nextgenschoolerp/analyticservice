package com.kvs.analytics.repo;

import com.kvs.analytics.entity.SystemUptimeSla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemUptimeSlaRepository extends JpaRepository<SystemUptimeSla, UUID> {
    List<SystemUptimeSla> findByTenantId(UUID tenantId);
    Optional<SystemUptimeSla> findByTenantIdAndPeriodMonthYear(UUID tenantId, String periodMonthYear);
}
