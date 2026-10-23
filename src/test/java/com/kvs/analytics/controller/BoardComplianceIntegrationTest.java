package com.kvs.analytics.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kvs.analytics.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardComplianceIntegrationTest extends AnalyticsTestSupport {

    @Test
    @DisplayName("US-10005, US-10013, US-10017: Board Regulatory Compliance and Data Privacy")
    void testBoardComplianceSuite() throws Exception {
        String adminToken = token("SUPER_ADMIN");
        String studentToken = token("STUDENT");

        // 1. US-10005: CBSE List of Candidates (LOC) Export
        CbseLocExportRequest locReq = CbseLocExportRequest.builder()
                .tenantId(tenantId)
                .academicYearId(UUID.randomUUID())
                .className("Class 10")
                .exportFormat("CBSE_TEXT_FORMAT")
                .build();

        // RBAC check
        postJson("/compliance/cbse-loc/export", studentToken, locReq)
                .andExpect(status().isForbidden());

        JsonNode locJson = data(postJson("/compliance/cbse-loc/export", adminToken, locReq)
                .andExpect(status().isCreated()));
        assertThat(locJson.get("candidateCount").asInt()).isEqualTo(116);
        assertThat(locJson.get("rawLocDataPreview").asText()).contains("CBSE-AFF-2130092");

        JsonNode locList = data(getAs("/compliance/cbse-loc/exports", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(locList.size()).isGreaterThanOrEqualTo(1);

        // 2. US-10013: Production Data Anonymization Engine
        AnonymizationRequest anonReq = AnonymizationRequest.builder()
                .tenantId(tenantId)
                .jobName("Weekly QA Staging Seed Refresh")
                .targetStagingDb("kvs_school_erp_staging")
                .rulesAppliedJson("{\"students.phone\":\"+91980000XXXX\",\"parents.email\":\"anonymized_parent@kvs.local\"}")
                .build();

        JsonNode anonJson = data(postJson("/compliance/anonymization/execute", adminToken, anonReq)
                .andExpect(status().isCreated()));
        assertThat(anonJson.get("status").asText()).isEqualTo("COMPLETED");
        assertThat(anonJson.get("recordsMasked").asLong()).isEqualTo(12450L);

        JsonNode anonList = data(getAs("/compliance/anonymization/jobs", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(anonList.size()).isGreaterThanOrEqualTo(1);

        // 3. US-10017: Board Regulatory Compliance Certificates
        ComplianceCertificateRequest certReq = ComplianceCertificateRequest.builder()
                .tenantId(tenantId)
                .certificateType("FIRE_SAFETY_NOC")
                .certificateNumber("DFS/NOC/HQ/2026/8941")
                .issuingAuthority("State Fire & Emergency Services Dept")
                .issueDate(Instant.now().minus(90, ChronoUnit.DAYS))
                .validUntil(Instant.now().plus(275, ChronoUnit.DAYS))
                .documentUrl("https://storage.kvs.local/compliance/fire_noc_2026.pdf")
                .verificationNotes("Annual site inspection cleared with zero structural non-conformities.")
                .build();

        JsonNode certJson = data(postJson("/compliance/certificates", adminToken, certReq)
                .andExpect(status().isCreated()));
        assertThat(certJson.get("certificateNumber").asText()).isEqualTo("DFS/NOC/HQ/2026/8941");
        assertThat(certJson.get("status").asText()).isEqualTo("VALID");

        JsonNode certList = data(getAs("/compliance/certificates", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(certList.size()).isGreaterThanOrEqualTo(1);
    }
}
