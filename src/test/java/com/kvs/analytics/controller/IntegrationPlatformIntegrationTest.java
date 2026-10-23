package com.kvs.analytics.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.enums.IntegrationAuthType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IntegrationPlatformIntegrationTest extends AnalyticsTestSupport {

    @Test
    @DisplayName("US-10000, US-10015, US-10011, US-10010: Integration Platform and External Developer APIs")
    void testIntegrationPlatformSuite() throws Exception {
        String adminToken = token("SUPER_ADMIN");
        String teacherToken = token("TEACHER");

        // 1. US-10000: Register REST Endpoint - RBAC check
        IntegrationEndpointRequest epReq = IntegrationEndpointRequest.builder()
                .tenantId(tenantId)
                .endpointName("LMS Moodle Sync")
                .targetUrl("https://moodle.partner.edu/api/v2/roster")
                .httpMethod("POST")
                .authType(IntegrationAuthType.BEARER_TOKEN)
                .headersJson("{\"X-Partner-Id\":\"KVS-01\"}")
                .description("Synchronize course registrations")
                .build();

        // Forbidden for teacher
        postJson("/integrations/endpoints", teacherToken, epReq)
                .andExpect(status().isForbidden());

        // Allowed for admin
        JsonNode epJson = data(postJson("/integrations/endpoints", adminToken, epReq)
                .andExpect(status().isCreated()));
        String endpointId = epJson.get("id").asText();
        assertThat(endpointId).isNotBlank();
        assertThat(epJson.get("endpointName").asText()).isEqualTo("LMS Moodle Sync");

        // List endpoints
        JsonNode epList = data(getAs("/integrations/endpoints", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(epList.size()).isGreaterThanOrEqualTo(1);

        // Get endpoint by id
        JsonNode singleEp = data(getAs("/integrations/endpoints/" + endpointId, adminToken)
                .andExpect(status().isOk()));
        assertThat(singleEp.get("id").asText()).isEqualTo(endpointId);

        // 2. US-10015: Generate API Key
        ApiKeyCreateRequest keyReq = ApiKeyCreateRequest.builder()
                .tenantId(tenantId)
                .keyName("RFID Gate Integration Key")
                .allowedScopes("ATTENDANCE_READ,ATTENDANCE_WRITE")
                .rateLimitTier("PREMIUM")
                .build();

        JsonNode keyJson = data(postJson("/integrations/api-keys", adminToken, keyReq)
                .andExpect(status().isCreated()));
        String keyId = keyJson.get("id").asText();
        assertThat(keyId).isNotBlank();
        assertThat(keyJson.get("clientId").asText()).startsWith("kvs_cli_");
        assertThat(keyJson.get("rawSecretToken").asText()).startsWith("kvs_sec_");

        // List keys
        JsonNode keyList = data(getAs("/integrations/api-keys", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(keyList.size()).isGreaterThanOrEqualTo(1);

        // Revoke key
        deleteAs("/integrations/api-keys/" + keyId, adminToken)
                .andExpect(status().isOk());

        // 3. US-10011: Rate Limit Policy
        RateLimitPolicyRequest rlReq = RateLimitPolicyRequest.builder()
                .tenantId(tenantId)
                .policyName("Mobile App Public Throttle")
                .clientIdentifier("mobile_gateway_pool")
                .windowSeconds(60)
                .maxRequests(300)
                .enforcementAction("THROTTLE")
                .build();

        JsonNode rlJson = data(postJson("/integrations/rate-limits", adminToken, rlReq)
                .andExpect(status().isCreated()));
        assertThat(rlJson.get("policyName").asText()).isEqualTo("Mobile App Public Throttle");

        JsonNode rlList = data(getAs("/integrations/rate-limits", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(rlList.size()).isGreaterThanOrEqualTo(1);

        // 4. US-10010: Latency Alert Rule
        LatencyRuleRequest latReq = LatencyRuleRequest.builder()
                .tenantId(tenantId)
                .ruleName("Core Fee Payment SLA Warning")
                .endpointPattern("/api/v1/financeservice/transactions/**")
                .thresholdMs(450L)
                .consecutiveBreaches(3)
                .alertWebhookUrl("https://opsgenie.schoolerp.internal/alerts/kvs-infra")
                .build();

        JsonNode latJson = data(postJson("/integrations/latency-rules", adminToken, latReq)
                .andExpect(status().isCreated()));
        assertThat(latJson.get("thresholdMs").asLong()).isEqualTo(450L);

        JsonNode latList = data(getAs("/integrations/latency-rules", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(latList.size()).isGreaterThanOrEqualTo(1);
    }
}
