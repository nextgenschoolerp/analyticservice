package com.kvs.analytics.repo;

import com.kvs.analytics.entity.CbseLocExportConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CbseLocExportConfigRepository extends JpaRepository<CbseLocExportConfig, UUID> {
    List<CbseLocExportConfig> findByTenantId(String tenantId);
    List<CbseLocExportConfig> findByTenantIdAndAcademicYearId(String tenantId, UUID academicYearId);
}
