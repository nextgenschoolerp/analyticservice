package com.kvs.analytics.controller;

import com.kvs.analytics.config.AnalyticsRoles;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.service.SystemHealthAndAuditService;
import com.kvs.core.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST controller for Module 10 System Health, Security Auditing & Resilience:
 * - US-10006: Real-Time Server Health Monitoring
 * - US-10007: Immutable Database Modification Audit Trail
 * - US-10008: Automated Cloud Database Backup & PITR
 * - US-10016: Slow Query Profiling & Index Optimization Plans
 * - US-10018: Database Replica Failover Cluster Management
 * - US-10019: System Uptime SLAs & Public Status Incident Reporting
 */
@RestController
@RequestMapping("/api/v1/analytics/system")
@RequiredArgsConstructor
public class SystemHealthAndAuditController {

    private final SystemHealthAndAuditService systemService;

    // --- US-10006: Real-Time Server Health Monitoring ---
    @PostMapping("/health/telemetry")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<SystemHealthMetricResponse>> recordHealthMetric(
            @Valid @RequestBody SystemHealthMetricRequest request) {
        SystemHealthMetricResponse response = systemService.recordHealthMetric(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<SystemHealthMetricResponse>builder()
                        .success(true)
                        .message("Server telemetry metric recorded successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/health/latest")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<SystemHealthMetricResponse>> getLatestHealth(
            @RequestParam UUID tenantId) {
        SystemHealthMetricResponse response = systemService.getLatestHealth(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<SystemHealthMetricResponse>builder()
                        .success(true)
                        .message("Latest server node health status retrieved")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/health/history")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<List<SystemHealthMetricResponse>>> getHealthHistory(
            @RequestParam UUID tenantId) {
        List<SystemHealthMetricResponse> response = systemService.getHealthHistory(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<SystemHealthMetricResponse>>builder()
                        .success(true)
                        .message("System health telemetry history retrieved")
                        .data(response)
                        .build()
        );
    }

    // --- US-10007: Immutable Database Modification Audit Trail ---
    @PostMapping("/audit/logs")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<DataModificationAuditResponse>> recordAuditLog(
            @Valid @RequestBody DataModificationAuditRequest request) {
        DataModificationAuditResponse response = systemService.recordAuditLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<DataModificationAuditResponse>builder()
                        .success(true)
                        .message("Immutable forensic modification audit entry sealed")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/audit/logs")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<List<DataModificationAuditResponse>>> listAuditLogs(
            @RequestParam UUID tenantId) {
        List<DataModificationAuditResponse> response = systemService.listAuditLogs(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<DataModificationAuditResponse>>builder()
                        .success(true)
                        .message("Database modification audit trail retrieved")
                        .data(response)
                        .build()
        );
    }

    // --- US-10008: Automated Cloud Database Backup & PITR ---
    @PostMapping("/backups")
    @PreAuthorize(AnalyticsRoles.BACKUP_MANAGE)
    public ResponseEntity<ApiResponse<CloudBackupResponse>> triggerCloudBackup(
            @Valid @RequestBody CloudBackupRequest request) {
        CloudBackupResponse response = systemService.triggerCloudBackup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CloudBackupResponse>builder()
                        .success(true)
                        .message("Cloud backup snapshot completed and validated with SHA-256 checksum")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/backups")
    @PreAuthorize(AnalyticsRoles.BACKUP_MANAGE)
    public ResponseEntity<ApiResponse<List<CloudBackupResponse>>> listBackups(
            @RequestParam UUID tenantId) {
        List<CloudBackupResponse> response = systemService.listBackups(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<CloudBackupResponse>>builder()
                        .success(true)
                        .message("Cloud backup snapshots retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10016: Slow Query Profiling & Index Optimization Plans ---
    @GetMapping("/performance/slow-queries")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSlowQueryRecommendations() {
        List<Map<String, Object>> response = systemService.getSlowQueryRecommendations();
        return ResponseEntity.ok(
                ApiResponse.<List<Map<String, Object>>>builder()
                        .success(true)
                        .message("Database slow query analysis and index recommendations retrieved")
                        .data(response)
                        .build()
        );
    }

    // --- US-10018: Database Replica Failover Cluster Management ---
    @PostMapping("/replicas/failover")
    @PreAuthorize(AnalyticsRoles.BACKUP_MANAGE)
    public ResponseEntity<ApiResponse<ReplicaNodeResponse>> executeReplicaFailover(
            @Valid @RequestBody ReplicaFailoverRequest request) {
        ReplicaNodeResponse response = systemService.executeReplicaFailover(request);
        return ResponseEntity.ok(
                ApiResponse.<ReplicaNodeResponse>builder()
                        .success(true)
                        .message("Database failover completed. New primary replica node active.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/replicas")
    @PreAuthorize(AnalyticsRoles.BACKUP_MANAGE)
    public ResponseEntity<ApiResponse<List<ReplicaNodeResponse>>> getReplicaStatus(
            @RequestParam UUID tenantId) {
        List<ReplicaNodeResponse> response = systemService.getReplicaStatus(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<ReplicaNodeResponse>>builder()
                        .success(true)
                        .message("Database replica nodes health cluster status retrieved")
                        .data(response)
                        .build()
        );
    }

    // --- US-10019: System Uptime SLAs & Public Status Incident Reporting ---
    @PostMapping("/uptime/sla")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<UptimeSlaResponse>> recordUptimeSla(
            @Valid @RequestBody UptimeSlaRequest request) {
        UptimeSlaResponse response = systemService.recordUptimeSla(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<UptimeSlaResponse>builder()
                        .success(true)
                        .message("Monthly system uptime SLA recorded successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/uptime/sla")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<List<UptimeSlaResponse>>> listUptimeSlas(
            @RequestParam UUID tenantId) {
        List<UptimeSlaResponse> response = systemService.listUptimeSlas(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<UptimeSlaResponse>>builder()
                        .success(true)
                        .message("System uptime SLA history retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/status/incidents")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<StatusIncidentResponse>> createIncident(
            @Valid @RequestBody StatusIncidentRequest request) {
        StatusIncidentResponse response = systemService.createIncident(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<StatusIncidentResponse>builder()
                        .success(true)
                        .message("System status incident published to public status dashboard")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/status/incidents")
    @PreAuthorize(AnalyticsRoles.AUDIT_VIEW)
    public ResponseEntity<ApiResponse<List<StatusIncidentResponse>>> listIncidents(
            @RequestParam UUID tenantId) {
        List<StatusIncidentResponse> response = systemService.listIncidents(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<StatusIncidentResponse>>builder()
                        .success(true)
                        .message("Status incident notices retrieved successfully")
                        .data(response)
                        .build()
        );
    }
}
