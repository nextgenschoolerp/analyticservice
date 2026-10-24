package com.kvs.analytics.service;

import com.kvs.analytics.dto.*;
import com.kvs.analytics.entity.*;
import com.kvs.analytics.repo.*;
import com.kvs.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class IntegrationPlatformService {

    private final ApiIntegrationEndpointRepository endpointRepo;
    private final ApiKeyCredentialRepository apiKeyRepo;
    private final ApiRateLimitPolicyRepository rateLimitRepo;
    private final ApiLatencyRuleRepository latencyRuleRepo;

    // --- US-10000: Publish RESTful API Endpoints ---
    public IntegrationEndpointResponse createEndpoint(IntegrationEndpointRequest req) {
        log.info("Registering API endpoint {} for tenant {}", req.getEndpointName(), req.getTenantId());

        ApiIntegrationEndpoint endpoint = ApiIntegrationEndpoint.builder()
                .tenantId(req.getTenantId())
                .endpointName(req.getEndpointName())
                .targetUrl(req.getTargetUrl())
                .httpMethod(req.getHttpMethod().toUpperCase())
                .authType(req.getAuthType())
                .headersJson(req.getHeadersJson())
                .description(req.getDescription())
                .isActive(true)
                .build();

        endpoint = endpointRepo.save(endpoint);
        return mapToEndpointResponse(endpoint);
    }

    @Transactional(readOnly = true)
    public List<IntegrationEndpointResponse> listEndpoints(String tenantId) {
        return endpointRepo.findByTenantId(tenantId).stream()
                .map(this::mapToEndpointResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IntegrationEndpointResponse getEndpoint(UUID id) {
        ApiIntegrationEndpoint endpoint = endpointRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API endpoint not found with ID: " + id));
        return mapToEndpointResponse(endpoint);
    }

    // --- US-10015: API Keys & Credentials Management ---
    public ApiKeyResponse createApiKey(ApiKeyCreateRequest req) {
        log.info("Generating API key for client '{}' in tenant {}", req.getKeyName(), req.getTenantId());

        String clientId = "kvs_cli_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String rawSecret = "kvs_sec_" + generateSecureToken(32);
        String secretHash = hashSha256(rawSecret);
        String prefix = rawSecret.substring(0, 12);

        ApiKeyCredential key = ApiKeyCredential.builder()
                .tenantId(req.getTenantId())
                .keyName(req.getKeyName())
                .clientId(clientId)
                .apiKeyPrefix(prefix)
                .secretHash(secretHash)
                .allowedScopes(req.getAllowedScopes() != null ? req.getAllowedScopes() : "API_READ,ATTENDANCE_READ")
                .rateLimitTier(req.getRateLimitTier() != null ? req.getRateLimitTier() : "STANDARD")
                .expiresAt(req.getExpiresAt() != null ? req.getExpiresAt() : Instant.now().plusSeconds(86400 * 365))
                .isRevoked(false)
                .build();

        key = apiKeyRepo.save(key);

        ApiKeyResponse res = mapToApiKeyResponse(key);
        res.setRawSecretToken(rawSecret); // Returned once on creation
        return res;
    }

    @Transactional(readOnly = true)
    public List<ApiKeyResponse> listApiKeys(String tenantId) {
        return apiKeyRepo.findByTenantId(tenantId).stream()
                .map(this::mapToApiKeyResponse)
                .collect(Collectors.toList());
    }

    public void revokeApiKey(UUID id) {
        ApiKeyCredential key = apiKeyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API key not found: " + id));
        key.setIsRevoked(true);
        apiKeyRepo.save(key);
        log.info("API key {} has been revoked", id);
    }

    // --- US-10011: Rate Limiting Policies ---
    public RateLimitPolicyResponse configureRateLimit(RateLimitPolicyRequest req) {
        log.info("Configuring rate limit policy for identifier '{}'", req.getClientIdentifier());

        ApiRateLimitPolicy policy = rateLimitRepo.findByTenantIdAndClientIdentifier(req.getTenantId(), req.getClientIdentifier())
                .orElse(ApiRateLimitPolicy.builder()
                        .tenantId(req.getTenantId())
                        .clientIdentifier(req.getClientIdentifier())
                        .build());

        policy.setPolicyName(req.getPolicyName());
        policy.setWindowSeconds(req.getWindowSeconds() != null ? req.getWindowSeconds() : 60);
        policy.setMaxRequests(req.getMaxRequests() != null ? req.getMaxRequests() : 100);
        policy.setEnforcementAction(req.getEnforcementAction() != null ? req.getEnforcementAction() : "THROTTLE");
        policy.setIsActive(true);

        policy = rateLimitRepo.save(policy);
        return mapToRateLimitResponse(policy);
    }

    @Transactional(readOnly = true)
    public List<RateLimitPolicyResponse> listRateLimits(String tenantId) {
        return rateLimitRepo.findByTenantId(tenantId).stream()
                .map(this::mapToRateLimitResponse)
                .collect(Collectors.toList());
    }

    // --- US-10010: Latency Alert Rules ---
    public LatencyRuleResponse configureLatencyRule(LatencyRuleRequest req) {
        log.info("Configuring latency alert rule for pattern '{}'", req.getEndpointPattern());

        ApiLatencyRule rule = ApiLatencyRule.builder()
                .tenantId(req.getTenantId())
                .ruleName(req.getRuleName())
                .endpointPattern(req.getEndpointPattern())
                .thresholdMs(req.getThresholdMs() != null ? req.getThresholdMs() : 500L)
                .consecutiveBreaches(req.getConsecutiveBreaches() != null ? req.getConsecutiveBreaches() : 3)
                .alertWebhookUrl(req.getAlertWebhookUrl())
                .isActive(true)
                .build();

        rule = latencyRuleRepo.save(rule);
        return mapToLatencyResponse(rule);
    }

    @Transactional(readOnly = true)
    public List<LatencyRuleResponse> listLatencyRules(String tenantId) {
        return latencyRuleRepo.findByTenantId(tenantId).stream()
                .map(this::mapToLatencyResponse)
                .collect(Collectors.toList());
    }

    // --- Helpers ---
    private IntegrationEndpointResponse mapToEndpointResponse(ApiIntegrationEndpoint e) {
        return IntegrationEndpointResponse.builder()
                .id(e.getId())
                .tenantId(e.getTenantId())
                .endpointName(e.getEndpointName())
                .targetUrl(e.getTargetUrl())
                .httpMethod(e.getHttpMethod())
                .authType(e.getAuthType())
                .headersJson(e.getHeadersJson())
                .isActive(e.getIsActive())
                .description(e.getDescription())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private ApiKeyResponse mapToApiKeyResponse(ApiKeyCredential k) {
        return ApiKeyResponse.builder()
                .id(k.getId())
                .tenantId(k.getTenantId())
                .keyName(k.getKeyName())
                .clientId(k.getClientId())
                .apiKeyPrefix(k.getApiKeyPrefix())
                .allowedScopes(k.getAllowedScopes())
                .rateLimitTier(k.getRateLimitTier())
                .expiresAt(k.getExpiresAt())
                .isRevoked(k.getIsRevoked())
                .createdAt(k.getCreatedAt())
                .build();
    }

    private RateLimitPolicyResponse mapToRateLimitResponse(ApiRateLimitPolicy p) {
        return RateLimitPolicyResponse.builder()
                .id(p.getId())
                .tenantId(p.getTenantId())
                .policyName(p.getPolicyName())
                .clientIdentifier(p.getClientIdentifier())
                .windowSeconds(p.getWindowSeconds())
                .maxRequests(p.getMaxRequests())
                .enforcementAction(p.getEnforcementAction())
                .isActive(p.getIsActive())
                .createdAt(p.getCreatedAt())
                .build();
    }

    private LatencyRuleResponse mapToLatencyResponse(ApiLatencyRule r) {
        return LatencyRuleResponse.builder()
                .id(r.getId())
                .tenantId(r.getTenantId())
                .ruleName(r.getRuleName())
                .endpointPattern(r.getEndpointPattern())
                .thresholdMs(r.getThresholdMs())
                .consecutiveBreaches(r.getConsecutiveBreaches())
                .alertWebhookUrl(r.getAlertWebhookUrl())
                .isActive(r.getIsActive())
                .createdAt(r.getCreatedAt())
                .build();
    }

    private String generateSecureToken(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("SHA-256 algorithm missing", ex);
        }
    }
}
