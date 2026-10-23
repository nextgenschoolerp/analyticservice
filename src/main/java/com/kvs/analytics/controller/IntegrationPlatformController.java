package com.kvs.analytics.controller;

import com.kvs.analytics.config.AnalyticsRoles;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.service.IntegrationPlatformService;
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
 * REST controller for Module 10 Integration Platform & External APIs:
 * - US-10000: Publish RESTful API Endpoints
 * - US-10010: API Latency Alert Rules
 * - US-10011: API Rate Limiting Policies
 * - US-10015: API Keys & Credentials Management
 */
@RestController
@RequestMapping("/api/v1/analytics/integrations")
@RequiredArgsConstructor
public class IntegrationPlatformController {

    private final IntegrationPlatformService integrationService;

    // --- US-10000: Endpoints ---
    @PostMapping("/endpoints")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<IntegrationEndpointResponse>> createEndpoint(
            @Valid @RequestBody IntegrationEndpointRequest request) {
        IntegrationEndpointResponse response = integrationService.createEndpoint(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<IntegrationEndpointResponse>builder()
                        .success(true)
                        .message("API integration endpoint registered successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/endpoints")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<List<IntegrationEndpointResponse>>> listEndpoints(
            @RequestParam UUID tenantId) {
        List<IntegrationEndpointResponse> response = integrationService.listEndpoints(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<IntegrationEndpointResponse>>builder()
                        .success(true)
                        .message("API integration endpoints retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/endpoints/{id}")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<IntegrationEndpointResponse>> getEndpoint(
            @PathVariable UUID id) {
        IntegrationEndpointResponse response = integrationService.getEndpoint(id);
        return ResponseEntity.ok(
                ApiResponse.<IntegrationEndpointResponse>builder()
                        .success(true)
                        .message("API endpoint details retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10015: API Keys ---
    @PostMapping("/api-keys")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<ApiKeyResponse>> createApiKey(
            @Valid @RequestBody ApiKeyCreateRequest request) {
        ApiKeyResponse response = integrationService.createApiKey(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ApiKeyResponse>builder()
                        .success(true)
                        .message("API credential generated successfully. Secret is displayed only once.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/api-keys")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<List<ApiKeyResponse>>> listApiKeys(
            @RequestParam UUID tenantId) {
        List<ApiKeyResponse> response = integrationService.listApiKeys(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<ApiKeyResponse>>builder()
                        .success(true)
                        .message("Tenant API credentials retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/api-keys/{id}")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<Void>> revokeApiKey(
            @PathVariable UUID id) {
        integrationService.revokeApiKey(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("API key revoked successfully")
                        .build()
        );
    }

    // --- US-10011: Rate Limiting ---
    @PostMapping("/rate-limits")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<RateLimitPolicyResponse>> configureRateLimit(
            @Valid @RequestBody RateLimitPolicyRequest request) {
        RateLimitPolicyResponse response = integrationService.configureRateLimit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RateLimitPolicyResponse>builder()
                        .success(true)
                        .message("Rate limiting policy configured successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/rate-limits")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<List<RateLimitPolicyResponse>>> listRateLimits(
            @RequestParam UUID tenantId) {
        List<RateLimitPolicyResponse> response = integrationService.listRateLimits(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<RateLimitPolicyResponse>>builder()
                        .success(true)
                        .message("Rate limiting policies retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10010: Latency Alerts ---
    @PostMapping("/latency-rules")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<LatencyRuleResponse>> configureLatencyRule(
            @Valid @RequestBody LatencyRuleRequest request) {
        LatencyRuleResponse response = integrationService.configureLatencyRule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<LatencyRuleResponse>builder()
                        .success(true)
                        .message("API latency alert rule configured successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/latency-rules")
    @PreAuthorize(AnalyticsRoles.INTEGRATION_MANAGE)
    public ResponseEntity<ApiResponse<List<LatencyRuleResponse>>> listLatencyRules(
            @RequestParam UUID tenantId) {
        List<LatencyRuleResponse> response = integrationService.listLatencyRules(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<LatencyRuleResponse>>builder()
                        .success(true)
                        .message("Latency alert rules retrieved successfully")
                        .data(response)
                        .build()
        );
    }
}
