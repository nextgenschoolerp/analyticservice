package com.kvs.analytics.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kvs.analytics.dto.*;
import com.kvs.analytics.enums.DeviceDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HardwareAndWebhooksIntegrationTest extends AnalyticsTestSupport {

    @Test
    @DisplayName("US-10001, US-10002, US-10012, US-10003, US-10004: Hardware Connectors and Webhook Integration")
    void testHardwareAndWebhooksSuite() throws Exception {
        String adminToken = token("SUPER_ADMIN");
        String parentToken = token("PARENT");

        // 1. US-10001: Biometric Terminal Devices
        BiometricDeviceRequest devReq = BiometricDeviceRequest.builder()
                .tenantId(tenantId)
                .deviceSerialNumber("ZK-SN-849201")
                .deviceName("Senior Wing Turnstile 01")
                .ipAddress("192.168.10.45")
                .portNumber(4370)
                .location("Main Gate Turnstile A")
                .deviceModel("ZKTeco ProCapture-X")
                .build();

        // RBAC Check
        postJson("/connectors/biometric/devices", parentToken, devReq)
                .andExpect(status().isForbidden());

        JsonNode devJson = data(postJson("/connectors/biometric/devices", adminToken, devReq)
                .andExpect(status().isCreated()));
        assertThat(devJson.get("deviceSerialNumber").asText()).isEqualTo("ZK-SN-849201");
        assertThat(devJson.get("isOnline").asBoolean()).isTrue();

        JsonNode devList = data(getAs("/connectors/biometric/devices", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(devList.size()).isGreaterThanOrEqualTo(1);

        // Biometric Attendance Punch Stream Ingest
        AttendanceStreamEventRequest streamReq = AttendanceStreamEventRequest.builder()
                .tenantId(tenantId)
                .deviceSerialNumber("ZK-SN-849201")
                .userBiometricId("BIO_STU_10482")
                .punchTimestamp(Instant.now())
                .direction(DeviceDirection.IN)
                .verificationType("FINGERPRINT")
                .build();

        JsonNode streamJson = data(postJson("/connectors/biometric/attendance-stream", adminToken, streamReq)
                .andExpect(status().isCreated()));
        assertThat(streamJson.get("userBiometricId").asText()).isEqualTo("BIO_STU_10482");
        assertThat(streamJson.get("isProcessed").asBoolean()).isTrue();

        JsonNode streamList = data(getAs("/connectors/biometric/attendance-stream", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(streamList.size()).isGreaterThanOrEqualTo(1);

        // 2. US-10002: Payment Gateway Webhook Handling
        PaymentWebhookRequest payReq = PaymentWebhookRequest.builder()
                .tenantId(tenantId)
                .gatewayProvider("RAZORPAY")
                .gatewayEventId("evt_pay_rzp_9941")
                .eventType("payment.captured")
                .transactionReference("TXN-PAY-2026-901")
                .amount(new BigDecimal("15000.00"))
                .currency("INR")
                .payloadJson("{\"id\":\"pay_9941\",\"status\":\"captured\"}")
                .signatureHeader("sha256=abcdef1234567890")
                .build();

        JsonNode payJson = data(postJson("/connectors/webhooks/payment", adminToken, payReq)
                .andExpect(status().isCreated()));
        assertThat(payJson.get("clearanceStatus").asText()).isEqualTo("CLEARED");
        assertThat(payJson.get("clearanceNotes").asText()).contains("Ledger updated instantly");

        JsonNode payList = data(getAs("/connectors/webhooks/payment", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(payList.size()).isGreaterThanOrEqualTo(1);

        // 3. US-10012: Bank Statement Reconciliation
        BankReconVerifyRequest reconReq = BankReconVerifyRequest.builder()
                .tenantId(tenantId)
                .statementTransactionId("HDFC-STMT-99401")
                .erpTransactionRef("TXN-PAY-2026-901")
                .bankAmount(new BigDecimal("15000.00"))
                .erpLedgerAmount(new BigDecimal("15000.00"))
                .transactionDate(Instant.now())
                .build();

        JsonNode reconJson = data(postJson("/connectors/bank-recon/verify", adminToken, reconReq)
                .andExpect(status().isCreated()));
        assertThat(reconJson.get("reconciliationStatus").asText()).isEqualTo("MATCHED");
        assertThat(reconJson.get("differenceAmount").asDouble()).isEqualTo(0.0);

        JsonNode reconList = data(getAs("/connectors/bank-recon", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(reconList.size()).isGreaterThanOrEqualTo(1);

        // 4. US-10003: WhatsApp Business Dispatch & Fallback
        WhatsAppDispatchRequest waReq = WhatsAppDispatchRequest.builder()
                .tenantId(tenantId)
                .recipientPhone("+919876543210")
                .templateName("fee_due_reminder_v1")
                .messageContent("Your term 1 fee of INR 15000 is due on 31-Oct.")
                .enableSmsFailover(true)
                .build();

        JsonNode waJson = data(postJson("/connectors/whatsapp/dispatch", adminToken, waReq)
                .andExpect(status().isOk()));
        assertThat(waJson.get("messageStatus").asText()).isEqualTo("DELIVERED");
        assertThat(waJson.get("channelUsed").asText()).isEqualTo("SMS_FAILOVER");

        // 5. US-10004: Tally Prime Voucher XML Synchronization
        TallySyncRequest tallyReq = TallySyncRequest.builder()
                .tenantId(tenantId)
                .voucherType("RECEIPT")
                .build();

        JsonNode tallyJson = data(postJson("/connectors/tally/sync", adminToken, tallyReq)
                .andExpect(status().isCreated()));
        assertThat(tallyJson.get("status").asText()).isEqualTo("COMPLETED");
        assertThat(tallyJson.get("xmlPayload").asText()).contains("<TALLYREQUEST>Import Data</TALLYREQUEST>");

        JsonNode tallyList = data(getAs("/connectors/tally/batches", adminToken, "tenantId", tenantId.toString())
                .andExpect(status().isOk()));
        assertThat(tallyList.size()).isGreaterThanOrEqualTo(1);
    }
}
