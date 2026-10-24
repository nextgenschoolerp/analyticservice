package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CbseLocExportResponse {
    private UUID id;
    private String tenantId;
    private UUID academicYearId;
    private String className;
    private Integer candidateCount;
    private String exportFormat;
    private String generatedFileUrl;
    private String rawLocDataPreview;
    private Instant createdAt;
}
