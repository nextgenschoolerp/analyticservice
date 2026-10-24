package com.kvs.analytics.repo;

import com.kvs.analytics.entity.DataModificationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataModificationAuditRepository extends JpaRepository<DataModificationAudit, UUID> {
    List<DataModificationAudit> findByTenantIdOrderByCreatedAtDesc(String tenantId);
    List<DataModificationAudit> findByTenantIdAndTableName(String tenantId, String tableName);
}
