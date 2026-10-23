package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceCertificateRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "certificateType is mandatory (e.g. FIRE_SAFETY, SANITARY_HYGIENE, BUILDING_SAFETY)")
    private String certificateType;

    @NotBlank(message = "certificateNumber is mandatory")
    private String certificateNumber;

    @NotBlank(message = "issuingAuthority is mandatory")
    private String issuingAuthority;

    @NotNull(message = "issueDate is mandatory")
    private Instant issueDate;

    @NotNull(message = "validUntil is mandatory")
    private Instant validUntil;

    private String documentUrl;
    private String verificationNotes;
}
