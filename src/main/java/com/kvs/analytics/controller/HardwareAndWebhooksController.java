package com.kvs.analytics.controller;

import com.kvs.analytics.config.AnalyticsRoles;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.entity.WhatsAppGatewayConfig;
import com.kvs.analytics.service.HardwareAndWebhookService;
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
 * REST controller for Module 10 Hardware Connectors & Webhook Integration:
 * - US-10001: Biometric Hardware Connector
 * - US-10002: Payment Gateway Webhook Handlers
 * - US-10003: WhatsApp Business API & Failover
 * - US-10004: Tally Prime Voucher XML Synchronization
 * - US-10012: Bank Statement Reconciliation
 */
@RestController
@RequestMapping("/api/v1/analytics/connectors")
@RequiredArgsConstructor
public class HardwareAndWebhooksController {

    private final HardwareAndWebhookService hardwareService;

    // --- US-10001: Biometric Devices & Attendance Stream ---
    @PostMapping("/biometric/devices")
    @PreAuthorize(AnalyticsRoles.GATEWAY_INGEST)
    public ResponseEntity<ApiResponse<BiometricDeviceResponse>> registerDevice(
            @Valid @RequestBody BiometricDeviceRequest request) {
        BiometricDeviceResponse response = hardwareService.registerDevice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<BiometricDeviceResponse>builder()
                        .success(true)
                        .message("Biometric terminal device registered successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/biometric/devices")
    @PreAuthorize(AnalyticsRoles.GATEWAY_INGEST)
    public ResponseEntity<ApiResponse<List<BiometricDeviceResponse>>> listDevices(
            @RequestParam UUID tenantId) {
        List<BiometricDeviceResponse> response = hardwareService.listDevices(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<BiometricDeviceResponse>>builder()
                        .success(true)
                        .message("Biometric terminal devices retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/biometric/attendance-stream")
    @PreAuthorize(AnalyticsRoles.GATEWAY_INGEST)
    public ResponseEntity<ApiResponse<AttendanceStreamEventResponse>> ingestAttendanceStream(
            @Valid @RequestBody AttendanceStreamEventRequest request) {
        AttendanceStreamEventResponse response = hardwareService.ingestAttendanceStream(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AttendanceStreamEventResponse>builder()
                        .success(true)
                        .message("Biometric punch event ingested and queued for attendance settlement")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/biometric/attendance-stream")
    @PreAuthorize(AnalyticsRoles.GATEWAY_INGEST)
    public ResponseEntity<ApiResponse<List<AttendanceStreamEventResponse>>> listAttendanceEvents(
            @RequestParam UUID tenantId) {
        List<AttendanceStreamEventResponse> response = hardwareService.listAttendanceEvents(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<AttendanceStreamEventResponse>>builder()
                        .success(true)
                        .message("Attendance stream punch events retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10002: Payment Gateway Webhook Ingestion ---
    @PostMapping("/webhooks/payment")
    @PreAuthorize(AnalyticsRoles.WEBHOOK_MANAGE)
    public ResponseEntity<ApiResponse<PaymentWebhookResponse>> processPaymentWebhook(
            @Valid @RequestBody PaymentWebhookRequest request) {
        PaymentWebhookResponse response = hardwareService.processPaymentWebhook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PaymentWebhookResponse>builder()
                        .success(true)
                        .message("Payment gateway webhook event cleared and reconciled")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/webhooks/payment")
    @PreAuthorize(AnalyticsRoles.WEBHOOK_MANAGE)
    public ResponseEntity<ApiResponse<List<PaymentWebhookResponse>>> listPaymentWebhooks(
            @RequestParam UUID tenantId) {
        List<PaymentWebhookResponse> response = hardwareService.listPaymentWebhooks(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<PaymentWebhookResponse>>builder()
                        .success(true)
                        .message("Payment gateway webhook logs retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10012: Bank Statement Reconciliation ---
    @PostMapping("/bank-recon/verify")
    @PreAuthorize(AnalyticsRoles.WEBHOOK_MANAGE)
    public ResponseEntity<ApiResponse<BankReconResponse>> verifyBankReconciliation(
            @Valid @RequestBody BankReconVerifyRequest request) {
        BankReconResponse response = hardwareService.verifyBankReconciliation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<BankReconResponse>builder()
                        .success(true)
                        .message("Bank statement transaction verified against ledger")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/bank-recon")
    @PreAuthorize(AnalyticsRoles.WEBHOOK_MANAGE)
    public ResponseEntity<ApiResponse<List<BankReconResponse>>> listBankReconciliation(
            @RequestParam UUID tenantId) {
        List<BankReconResponse> response = hardwareService.listBankReconciliation(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<BankReconResponse>>builder()
                        .success(true)
                        .message("Bank reconciliation records retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    // --- US-10003: WhatsApp Business Dispatch & Fallback ---
    @PostMapping("/whatsapp/dispatch")
    @PreAuthorize(AnalyticsRoles.GATEWAY_INGEST)
    public ResponseEntity<ApiResponse<WhatsAppDispatchResponse>> dispatchWhatsApp(
            @Valid @RequestBody WhatsAppDispatchRequest request) {
        WhatsAppDispatchResponse response = hardwareService.dispatchWhatsAppMessage(request);
        return ResponseEntity.ok(
                ApiResponse.<WhatsAppDispatchResponse>builder()
                        .success(true)
                        .message("Broadcast notification dispatched through verified WhatsApp channel")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/whatsapp/configs")
    @PreAuthorize(AnalyticsRoles.GATEWAY_INGEST)
    public ResponseEntity<ApiResponse<List<WhatsAppGatewayConfig>>> getWhatsAppConfigs(
            @RequestParam UUID tenantId) {
        List<WhatsAppGatewayConfig> response = hardwareService.getWhatsAppConfigs(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<WhatsAppGatewayConfig>>builder()
                        .success(true)
                        .message("WhatsApp gateway integration configs retrieved")
                        .data(response)
                        .build()
        );
    }

    // --- US-10004: Tally Prime Voucher XML Synchronization ---
    @PostMapping("/tally/sync")
    @PreAuthorize(AnalyticsRoles.WEBHOOK_MANAGE)
    public ResponseEntity<ApiResponse<TallySyncResponse>> triggerTallySync(
            @Valid @RequestBody TallySyncRequest request) {
        TallySyncResponse response = hardwareService.triggerTallySync(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<TallySyncResponse>builder()
                        .success(true)
                        .message("Tally Prime accounting XML batch generated successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/tally/batches")
    @PreAuthorize(AnalyticsRoles.WEBHOOK_MANAGE)
    public ResponseEntity<ApiResponse<List<TallySyncResponse>>> listTallyBatches(
            @RequestParam UUID tenantId) {
        List<TallySyncResponse> response = hardwareService.listTallyBatches(tenantId);
        return ResponseEntity.ok(
                ApiResponse.<List<TallySyncResponse>>builder()
                        .success(true)
                        .message("Tally Prime synchronization batches retrieved successfully")
                        .data(response)
                        .build()
        );
    }
}
