package com.kvs.analytics.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.enums.IncidentSeverity;
import com.kvs.analytics.enums.IncidentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SystemHealthAndReportingIntegrationTest extends AnalyticsTestSupport {

    @Test
    @DisplayName("US-10006, US-10007, US-10008, US-10016, US-10018, US-10019, US-10009, US-10014: System Health, Auditing, and BI Reporting")
    void testSystemHealthAndReportingSuite() throws Exception {
        String adminToken = token("SUPER_ADMIN");
        String ctoToken = token("CHIEF_TECHNOLOGY_OFFICER");
        String analystToken = token("DATA_ANALYST");

        // 1. US-10006: Server Health Telemetry
        SystemHealthMetricRequest healthReq = SystemHealthMetricRequest.builder()
                .tenantId(tenantId)
                .serverNodeId("kvs-prod-node-01")
                .cpuUsagePct(32.4)
                .memoryUsagePct(58.1)
                .activeDbConnections(24)
                .idleDbConnections(16)
                .diskUsagePct(48.2)
                .avgResponseLatencyMs(52L)
                .build();

        JsonNode healthJson = data(postJson("/system/health/telemetry", adminToken, healthReq)
                .andExpect(status().isCreated()));
        assertThat(healthJson.get("serverNodeId").asText()).isEqualTo("kvs-prod-node-01");

        JsonNode latestHealth = data(getAs("/system/health/latest", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(latestHealth.get("serverNodeId").asText()).isEqualTo("kvs-prod-node-01");

        JsonNode healthHistory = data(getAs("/system/health/history", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(healthHistory.size()).isGreaterThanOrEqualTo(1);

        // 2. US-10007: Immutable Database Modification Audit Trail
        DataModificationAuditRequest auditReq = DataModificationAuditRequest.builder()
                .tenantId(tenantId)
                .tableName("student_fee_ledgers")
                .operation("UPDATE")
                .recordId(UUID.randomUUID().toString())
                .oldValuesJson("{\"waiver_amount\":0}")
                .newValuesJson("{\"waiver_amount\":5000,\"approved_by\":\"Principal\"}")
                .modifiedByUsername("accounts_admin")
                .queryDurationMs(14L)
                .clientIp("10.14.20.101")
                .build();

        JsonNode auditJson = data(postJson("/system/audit/logs", adminToken, auditReq)
                .andExpect(status().isCreated()));
        assertThat(auditJson.get("tableName").asText()).isEqualTo("student_fee_ledgers");
        assertThat(auditJson.get("operation").asText()).isEqualTo("UPDATE");

        JsonNode auditList = data(getAs("/system/audit/logs", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(auditList.size()).isGreaterThanOrEqualTo(1);

        // 3. US-10008: Automated Cloud Database Backup & PITR
        CloudBackupRequest backupReq = CloudBackupRequest.builder()
                .tenantId(tenantId)
                .snapshotName("kvs_daily_automated_backup_0300")
                .cloudProvider("AWS_S3")
                .bucketLocation("s3://kvs-db-backups-mumbai/prod/2026-10-23/")
                .build();

        JsonNode backupJson = data(postJson("/system/backups", ctoToken, backupReq)
                .andExpect(status().isCreated()));
        assertThat(backupJson.get("status").asText()).isEqualTo("COMPLETED");
        assertThat(backupJson.get("checksumSha256").asText()).isNotBlank();

        JsonNode backupList = data(getAs("/system/backups", ctoToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(backupList.size()).isGreaterThanOrEqualTo(1);

        // 4. US-10016: Slow Query Profiling
        JsonNode slowQueries = data(getAs("/system/performance/slow-queries", ctoToken)
                .andExpect(status().isOk()));
        assertThat(slowQueries.size()).isGreaterThanOrEqualTo(1);
        assertThat(slowQueries.get(0).get("suggestedIndexSql").asText()).contains("CREATE INDEX");

        // 5. US-10018: Replica Node Failover
        ReplicaFailoverRequest failoverReq = ReplicaFailoverRequest.builder()
                .tenantId(tenantId)
                .targetHostname("kvs-db-replica-02.internal")
                .failoverReason("Scheduled primary node hardware maintenance")
                .build();

        JsonNode replicaJson = data(postJson("/system/replicas/failover", ctoToken, failoverReq)
                .andExpect(status().isOk()));
        assertThat(replicaJson.get("replicaRole").asText()).isEqualTo("PRIMARY");

        JsonNode replicaList = data(getAs("/system/replicas", ctoToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(replicaList.size()).isGreaterThanOrEqualTo(1);

        // 6. US-10019: System Uptime SLAs & Incidents
        UptimeSlaRequest slaReq = UptimeSlaRequest.builder()
                .tenantId(tenantId)
                .periodMonthYear("2026-09")
                .totalMinutes(43200)
                .downtimeMinutes(12)
                .slaTargetPct(99.9)
                .build();

        JsonNode slaJson = data(postJson("/system/uptime/sla", adminToken, slaReq)
                .andExpect(status().isCreated()));
        assertThat(slaJson.get("slaMet").asBoolean()).isTrue();
        assertThat(slaJson.get("uptimePercentage").asDouble()).isGreaterThan(99.9);

        JsonNode slaList = data(getAs("/system/uptime/sla", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(slaList.size()).isGreaterThanOrEqualTo(1);

        StatusIncidentRequest incReq = StatusIncidentRequest.builder()
                .tenantId(tenantId)
                .incidentTitle("SMS Gateway Carrier Transient Delays")
                .severity(IncidentSeverity.MINOR)
                .status(IncidentStatus.MONITORING)
                .impactedServices("SMS,NOTIFICATIONS")
                .incidentMessage("Carrier routes experiencing up to 3 minute delays; WhatsApp failover active.")
                .isPublic(true)
                .build();

        JsonNode incJson = data(postJson("/system/status/incidents", adminToken, incReq)
                .andExpect(status().isCreated()));
        assertThat(incJson.get("incidentTitle").asText()).isEqualTo("SMS Gateway Carrier Transient Delays");

        JsonNode incList = data(getAs("/system/status/incidents", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(incList.size()).isGreaterThanOrEqualTo(1);

        // 7. US-10009: Visual Drag-and-Drop SQL Report Builder
        CustomReportRequest reportReq = CustomReportRequest.builder()
                .tenantId(tenantId)
                .reportName("Term 1 Fee Realization by Grade")
                .category("FINANCE")
                .chartType("BAR")
                .dimensionsJson("[\"grade\", \"fee_head\"]")
                .metricsJson("[\"sum(demand_amount)\", \"sum(collected_amount)\"]")
                .filtersJson("{\"academic_term\":\"TERM_1\"}")
                .build();

        JsonNode reportJson = data(postJson("/reports/custom", analystToken, reportReq)
                .andExpect(status().isCreated()));
        String reportId = reportJson.get("id").asText();
        assertThat(reportId).isNotBlank();

        JsonNode reportList = data(getAs("/reports/custom", analystToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(reportList.size()).isGreaterThanOrEqualTo(1);

        // Execute dynamic report
        JsonNode execJson = data(postJson("/reports/custom/" + reportId + "/execute", analystToken, null)
                .andExpect(status().isOk()));
        assertThat(execJson.get("rowCount").asInt()).isGreaterThan(0);
        assertThat(execJson.get("columnHeaders").size()).isGreaterThan(0);

        // 8. US-10014: Trustee Executive KPI Dashboards
        TrusteeKpiRequest kpiReq = TrusteeKpiRequest.builder()
                .tenantId(tenantId)
                .periodName("Q2 FY 2026-27")
                .feeCollectionPct(96.8)
                .averageAttendancePct(95.2)
                .studentEnrollmentCount(1280)
                .teacherStudentRatio(17.8)
                .boardPassPercentage(99.2)
                .summaryNotes("Exceeded board academic targets and maintained 96%+ fee recovery.")
                .build();

        JsonNode kpiJson = data(postJson("/reports/trustee/kpi", analystToken, kpiReq)
                .andExpect(status().isCreated()));
        assertThat(kpiJson.get("periodName").asText()).isEqualTo("Q2 FY 2026-27");
        assertThat(kpiJson.get("boardPassPercentage").asDouble()).isEqualTo(99.2);

        JsonNode kpiList = data(getAs("/reports/trustee/kpi", analystToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(kpiList.size()).isGreaterThanOrEqualTo(1);
    }
}
