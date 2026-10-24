package com.kvs.analytics.controller;

import com.kvs.analytics.config.AnalyticsRoles;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.entity.CbseLocExportConfig;
import com.kvs.analytics.service.BoardComplianceService;
import com.kvs.core.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for Module 10 Regulatory Board Compliance & Privacy:
 * - US-10005: CBSE / ICSE List of Candidates (LOC) Export
 * - US-10013: Production Data Anonymization Engine
 * - US-10017: Board Regulatory Compliance Certificates
 */
@RestController
@RequestMapping("/api/v1/analytics/compliance")
@RequiredArgsConstructor
public class BoardComplianceController {

    private final BoardComplianceService complianceService;

    // --- US-10005: CBSE / ICSE List of Candidates (LOC) Export ---
    @PostMapping("/cbse-loc/export")
    @PreAuthorize(AnalyticsRoles.COMPLIANCE_MANAGE)
    public ResponseEntity<ApiResponse<CbseLocExportResponse>> exportCbseLoc(
            @Valid @RequestBody CbseLocExportRequest request) {
        CbseLocExportResponse response = complianceService.exportCbseLoc(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CbseLocExportResponse>builder()
                        .success(true)
                        .message("CBSE List of Candidates (LOC) batch generated and validated")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/cbse-loc/exports")
    @PreAuthorize(AnalyticsRoles.COMPLIANCE_MANAGE)
    public ResponseEntity<ApiResponse<List<CbseLocExportConfig>>> listLocExports(
            @RequestParam String tenantId) {
        List<CbseLocExportConfig> response = complianceService.listLocExports(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<CbseLocExportConfig>>builder()
                        .success(true)
                        .message("CBSE LOC export history retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10013: Production Data Anonymization Engine ---
    @PostMapping("/anonymization/execute")
    @PreAuthorize(AnalyticsRoles.COMPLIANCE_MANAGE)
    public ResponseEntity<ApiResponse<AnonymizationResponse>> executeAnonymization(
            @Valid @RequestBody AnonymizationRequest request) {
        AnonymizationResponse response = complianceService.executeAnonymization(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AnonymizationResponse>builder()
                        .success(true)
                        .message("Data anonymization pipeline executed successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/anonymization/jobs")
    @PreAuthorize(AnalyticsRoles.COMPLIANCE_MANAGE)
    public ResponseEntity<ApiResponse<List<AnonymizationResponse>>> listAnonymizationJobs(
            @RequestParam String tenantId) {
        List<AnonymizationResponse> response = complianceService.listAnonymizationJobs(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<AnonymizationResponse>>builder()
                        .success(true)
                        .message("Anonymization jobs history retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10017: Board Regulatory Compliance Certificates ---
    @PostMapping("/certificates")
    @PreAuthorize(AnalyticsRoles.COMPLIANCE_MANAGE)
    public ResponseEntity<ApiResponse<ComplianceCertificateResponse>> registerCertificate(
            @Valid @RequestBody ComplianceCertificateRequest request) {
        ComplianceCertificateResponse response = complianceService.registerCertificate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ComplianceCertificateResponse>builder()
                        .success(true)
                        .message("Regulatory compliance certificate recorded successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/certificates")
    @PreAuthorize(AnalyticsRoles.COMPLIANCE_MANAGE)
    public ResponseEntity<ApiResponse<List<ComplianceCertificateResponse>>> listCertificates(
            @RequestParam String tenantId) {
        List<ComplianceCertificateResponse> response = complianceService.listCertificates(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<ComplianceCertificateResponse>>builder()
                        .success(true)
                        .message("Compliance certificates retrieved successfully")
                        .data(response)
                        .build()
        );
    }
}
