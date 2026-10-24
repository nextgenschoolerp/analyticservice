package com.kvs.analytics.dto;

import com.kvs.analytics.enums.CertificateStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceCertificateResponse {
    private UUID id;
    private String tenantId;
    private String certificateType;
    private String certificateNumber;
    private String issuingAuthority;
    private Instant issueDate;
    private Instant validUntil;
    private String documentUrl;
    private CertificateStatus status;
    private String verificationNotes;
    private Instant createdAt;
}
