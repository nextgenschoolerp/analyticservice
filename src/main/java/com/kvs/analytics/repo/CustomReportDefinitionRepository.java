package com.kvs.analytics.repo;

import com.kvs.analytics.entity.CustomReportDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomReportDefinitionRepository extends JpaRepository<CustomReportDefinition, UUID> {
    List<CustomReportDefinition> findByTenantId(UUID tenantId);
    List<CustomReportDefinition> findByTenantIdAndCategory(UUID tenantId, String category);
}
