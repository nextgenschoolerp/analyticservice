package com.kvs.analytics.repo;

import com.kvs.analytics.entity.ComplianceAuditCertificate;
import com.kvs.analytics.enums.CertificateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComplianceAuditCertificateRepository extends JpaRepository<ComplianceAuditCertificate, UUID> {
    List<ComplianceAuditCertificate> findByTenantId(UUID tenantId);
    List<ComplianceAuditCertificate> findByTenantIdAndStatus(UUID tenantId, CertificateStatus status);
}
