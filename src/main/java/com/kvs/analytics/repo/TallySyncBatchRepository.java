package com.kvs.analytics.repo;

import com.kvs.analytics.entity.TallySyncBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TallySyncBatchRepository extends JpaRepository<TallySyncBatch, UUID> {
    List<TallySyncBatch> findByTenantId(UUID tenantId);
    Optional<TallySyncBatch> findByBatchNumber(String batchNumber);
}
