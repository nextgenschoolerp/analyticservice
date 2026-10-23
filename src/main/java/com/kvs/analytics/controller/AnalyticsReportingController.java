package com.kvs.analytics.controller;

import com.kvs.analytics.config.AnalyticsRoles;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.service.AnalyticsReportingService;
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
 * REST controller for Module 10 Custom BI Reports & Trustee Dashboards:
 * - US-10009: Visual Drag-and-Drop SQL Report Builder
 * - US-10014: Trustee Executive KPI Dashboards
 */
@RestController
@RequestMapping("/api/v1/analytics/reports")
@RequiredArgsConstructor
public class AnalyticsReportingController {

    private final AnalyticsReportingService reportingService;

    // --- US-10009: Visual Drag-and-Drop SQL Report Builder ---
    @PostMapping("/custom")
    @PreAuthorize(AnalyticsRoles.REPORT_BUILDER)
    public ResponseEntity<ApiResponse<CustomReportResponse>> createCustomReport(
            @Valid @RequestBody CustomReportRequest request) {
        CustomReportResponse response = reportingService.createCustomReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CustomReportResponse>builder()
                        .success(true)
                        .message("Custom report definition created and compiled successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/custom")
    @PreAuthorize(AnalyticsRoles.REPORT_BUILDER)
    public ResponseEntity<ApiResponse<List<CustomReportResponse>>> listCustomReports(
            @RequestParam UUID tenantId) {
        List<CustomReportResponse> response = reportingService.listCustomReports(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<CustomReportResponse>>builder()
                        .success(true)
                        .message("Custom report definitions retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/custom/{id}/execute")
    @PreAuthorize(AnalyticsRoles.REPORT_BUILDER)
    public ResponseEntity<ApiResponse<ReportExecutionResult>> executeReport(
            @PathVariable UUID id) {
        ReportExecutionResult response = reportingService.executeReport(id);
        return ResponseEntity.ok(
                ApiResponse.<ReportExecutionResult>builder()
                        .success(true)
                        .message("Analytical report query executed successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10014: Trustee Executive KPI Dashboards ---
    @PostMapping("/trustee/kpi")
    @PreAuthorize(AnalyticsRoles.ANALYTICS_VIEW)
    public ResponseEntity<ApiResponse<TrusteeKpiResponse>> recordTrusteeKpi(
            @Valid @RequestBody TrusteeKpiRequest request) {
        TrusteeKpiResponse response = reportingService.recordTrusteeKpi(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<TrusteeKpiResponse>builder()
                        .success(true)
                        .message("Executive trustee KPI benchmark snapshot recorded")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/trustee/kpi")
    @PreAuthorize(AnalyticsRoles.ANALYTICS_VIEW)
    public ResponseEntity<ApiResponse<List<TrusteeKpiResponse>>> listTrusteeDashboards(
            @RequestParam UUID tenantId) {
        List<TrusteeKpiResponse> response = reportingService.listTrusteeDashboards(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<TrusteeKpiResponse>>builder()
                        .success(true)
                        .message("Trustee KPI executive dashboards retrieved successfully")
                        .data(response)
                        .build()
        );
    }
}
