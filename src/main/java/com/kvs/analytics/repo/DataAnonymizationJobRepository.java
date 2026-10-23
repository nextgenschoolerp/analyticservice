package com.kvs.analytics.repo;

import com.kvs.analytics.entity.DataAnonymizationJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataAnonymizationJobRepository extends JpaRepository<DataAnonymizationJob, UUID> {
    List<DataAnonymizationJob> findByTenantId(UUID tenantId);
}
