package com.kvs.analytics.entity;

import com.kvs.analytics.enums.CertificateStatus;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_compliance_certificates", indexes = {
        @Index(name = "idx_cert_tenant", columnList = "tenant_id"),
        @Index(name = "idx_cert_type", columnList = "certificate_type"),
        @Index(name = "idx_cert_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceAuditCertificate extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "certificate_type", nullable = false, length = 100)
    private String certificateType;

    @Column(name = "certificate_number", nullable = false, length = 100)
    private String certificateNumber;

    @Column(name = "issuing_authority", nullable = false, length = 150)
    private String issuingAuthority;

    @Column(name = "issue_date", nullable = false)
    private Instant issueDate;

    @Column(name = "valid_until", nullable = false)
    private Instant validUntil;

    @Column(name = "document_url", length = 500)
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private CertificateStatus status = CertificateStatus.VALID;

    @Column(name = "verification_notes", length = 500)
    private String verificationNotes;
}
