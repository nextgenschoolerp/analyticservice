package com.kvs.analytics.repo;

import com.kvs.analytics.entity.BankReconciliationRecord;
import com.kvs.analytics.enums.ReconciliationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankReconciliationRecordRepository extends JpaRepository<BankReconciliationRecord, UUID> {
    List<BankReconciliationRecord> findByTenantId(UUID tenantId);
    List<BankReconciliationRecord> findByTenantIdAndReconciliationStatus(UUID tenantId, ReconciliationStatus status);
}
