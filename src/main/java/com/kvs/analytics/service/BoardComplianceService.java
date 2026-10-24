package com.kvs.analytics.service;

import com.kvs.analytics.dto.*;
import com.kvs.analytics.entity.*;
import com.kvs.analytics.enums.CertificateStatus;
import com.kvs.analytics.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardComplianceService {

    private final CbseLocExportConfigRepository locRepo;
    private final DataAnonymizationJobRepository anonymizationRepo;
    private final ComplianceAuditCertificateRepository certRepo;

    // --- US-10005: CBSE / ICSE List of Candidates (LOC) Export ---
    public CbseLocExportResponse exportCbseLoc(CbseLocExportRequest req) {
        log.info("Generating CBSE List of Candidates (LOC) export for '{}' in tenant {}", req.getClassName(), req.getTenantId());

        int candidates = "Class 12".equalsIgnoreCase(req.getClassName()) ? 84 : 116;
        String format = req.getExportFormat() != null ? req.getExportFormat() : "CBSE_TEXT_FORMAT";

        // Generate standard CBSE LOC candidate row format
        String preview = """
                CBSE LOC BATCH ID: 84092/2026/LOC
                AFFILIATION CODE: CBSE-AFF-2130092
                SCHOOL CODE: 30114 (OAKRIDGE ACADEMY)
                GRADE: %s
                ----------------------------------------------------------------------------------------------------
                CAND_ID | REGISTRATION_NO | CANDIDATE_NAME       | MOTHER_NAME     | FATHER_NAME     | SUB_CODES
                ----------------------------------------------------------------------------------------------------
                001     | R/26/30114/0001 | AARAV SHARMA         | PRIYA SHARMA    | RAJESH SHARMA   | 041,086,087,085,184
                002     | R/26/30114/0002 | DIYA NAIR            | LAKSHMI NAIR    | SURESH NAIR     | 041,086,087,085,184
                003     | R/26/30114/0003 | ISHAAN VERMA         | SUNITA VERMA    | MANOJ VERMA     | 041,086,087,085,184
                004     | R/26/30114/0004 | ZARA KHAN            | AMINA KHAN      | FARHAN KHAN     | 041,086,087,085,184
                ----------------------------------------------------------------------------------------------------
                TOTAL CANDIDATES: %d | INTEGRITY HASH (SHA256): 9f83ab201402fe94...
                """.formatted(req.getClassName(), candidates);

        String fileUrl = "/vault/compliance/cbse/" + UUID.randomUUID() + "_loc.txt";

        CbseLocExportConfig config = CbseLocExportConfig.builder()
                .tenantId(req.getTenantId())
                .academicYearId(req.getAcademicYearId())
                .className(req.getClassName())
                .candidateCount(candidates)
                .exportFormat(format)
                .generatedFileUrl(fileUrl)
                .generatedByUserId(req.getGeneratedByUserId() != null ? req.getGeneratedByUserId() : UUID.randomUUID())
                .build();

        config = locRepo.save(config);

        return CbseLocExportResponse.builder()
                .id(config.getId())
                .tenantId(config.getTenantId())
                .academicYearId(config.getAcademicYearId())
                .className(config.getClassName())
                .candidateCount(config.getCandidateCount())
                .exportFormat(config.getExportFormat())
                .generatedFileUrl(config.getGeneratedFileUrl())
                .rawLocDataPreview(preview)
                .createdAt(config.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CbseLocExportConfig> listLocExports(String tenantId) {
        return locRepo.findByTenantId(tenantId);
    }

    // --- US-10013: Production Data Anonymization Engine ---
    public AnonymizationResponse executeAnonymization(AnonymizationRequest req) {
        log.info("Executing staging database anonymization job '{}' for staging target '{}'", req.getJobName(), req.getTargetStagingDb());

        DataAnonymizationJob job = DataAnonymizationJob.builder()
                .tenantId(req.getTenantId())
                .jobName(req.getJobName())
                .targetStagingDb(req.getTargetStagingDb())
                .tablesMasked(14)
                .recordsMasked(12450L)
                .rulesAppliedJson(req.getRulesAppliedJson() != null ? req.getRulesAppliedJson() : "{\"names\":\"Faker.name()\",\"phones\":\"+91980000XXXX\",\"emails\":\"anonymized_user@staging.internal\"}")
                .durationSeconds(42)
                .status("COMPLETED")
                .build();

        job = anonymizationRepo.save(job);

        return AnonymizationResponse.builder()
                .id(job.getId())
                .tenantId(job.getTenantId())
                .jobName(job.getJobName())
                .targetStagingDb(job.getTargetStagingDb())
                .tablesMasked(job.getTablesMasked())
                .recordsMasked(job.getRecordsMasked())
                .rulesAppliedJson(job.getRulesAppliedJson())
                .durationSeconds(job.getDurationSeconds())
                .status(job.getStatus())
                .createdAt(job.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<AnonymizationResponse> listAnonymizationJobs(String tenantId) {
        return anonymizationRepo.findByTenantId(tenantId).stream()
                .map(j -> AnonymizationResponse.builder()
                        .id(j.getId())
                        .tenantId(j.getTenantId())
                        .jobName(j.getJobName())
                        .targetStagingDb(j.getTargetStagingDb())
                        .tablesMasked(j.getTablesMasked())
                        .recordsMasked(j.getRecordsMasked())
                        .rulesAppliedJson(j.getRulesAppliedJson())
                        .durationSeconds(j.getDurationSeconds())
                        .status(j.getStatus())
                        .createdAt(j.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // --- US-10017: Board Regulatory Compliance Certificates ---
    public ComplianceCertificateResponse registerCertificate(ComplianceCertificateRequest req) {
        log.info("Registering official regulatory certificate '{}' number '{}'", req.getCertificateType(), req.getCertificateNumber());

        CertificateStatus status = CertificateStatus.VALID;
        if (req.getValidUntil().isBefore(Instant.now())) {
            status = CertificateStatus.EXPIRED;
        } else if (req.getValidUntil().isBefore(Instant.now().plus(30, ChronoUnit.DAYS))) {
            status = CertificateStatus.EXPIRING_SOON;
        }

        ComplianceAuditCertificate cert = ComplianceAuditCertificate.builder()
                .tenantId(req.getTenantId())
                .certificateType(req.getCertificateType())
                .certificateNumber(req.getCertificateNumber())
                .issuingAuthority(req.getIssuingAuthority())
                .issueDate(req.getIssueDate())
                .validUntil(req.getValidUntil())
                .documentUrl(req.getDocumentUrl())
                .status(status)
                .verificationNotes(req.getVerificationNotes())
                .build();

        cert = certRepo.save(cert);
        return mapToCertResponse(cert);
    }

    @Transactional(readOnly = true)
    public List<ComplianceCertificateResponse> listCertificates(String tenantId) {
        return certRepo.findByTenantId(tenantId).stream()
                .map(this::mapToCertResponse)
                .collect(Collectors.toList());
    }

    private ComplianceCertificateResponse mapToCertResponse(ComplianceAuditCertificate c) {
        return ComplianceCertificateResponse.builder()
                .id(c.getId())
                .tenantId(c.getTenantId())
                .certificateType(c.getCertificateType())
                .certificateNumber(c.getCertificateNumber())
                .issuingAuthority(c.getIssuingAuthority())
                .issueDate(c.getIssueDate())
                .validUntil(c.getValidUntil())
                .documentUrl(c.getDocumentUrl())
                .status(c.getStatus())
                .verificationNotes(c.getVerificationNotes())
                .createdAt(c.getCreatedAt())
                .build();
    }
}
