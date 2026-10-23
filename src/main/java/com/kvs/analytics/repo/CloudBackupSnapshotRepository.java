package com.kvs.analytics.repo;

import com.kvs.analytics.entity.CloudBackupSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CloudBackupSnapshotRepository extends JpaRepository<CloudBackupSnapshot, UUID> {
    List<CloudBackupSnapshot> findByTenantIdOrderByPointInTimeDesc(UUID tenantId);
}
