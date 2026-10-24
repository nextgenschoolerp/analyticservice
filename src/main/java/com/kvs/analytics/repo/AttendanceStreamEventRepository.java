package com.kvs.analytics.repo;

import com.kvs.analytics.entity.AttendanceStreamEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceStreamEventRepository extends JpaRepository<AttendanceStreamEvent, UUID> {
    List<AttendanceStreamEvent> findByTenantId(String tenantId);
    List<AttendanceStreamEvent> findByTenantIdAndIsProcessedFalse(String tenantId);
}
