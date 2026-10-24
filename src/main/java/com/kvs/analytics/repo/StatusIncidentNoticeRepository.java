package com.kvs.analytics.repo;

import com.kvs.analytics.entity.StatusIncidentNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatusIncidentNoticeRepository extends JpaRepository<StatusIncidentNotice, UUID> {
    List<StatusIncidentNotice> findByTenantIdOrderByCreatedAtDesc(String tenantId);
    List<StatusIncidentNotice> findByTenantIdAndIsPublicTrueOrderByCreatedAtDesc(String tenantId);
}
