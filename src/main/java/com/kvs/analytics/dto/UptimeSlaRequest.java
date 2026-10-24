package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UptimeSlaRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "periodMonthYear is mandatory (e.g. 2026-10)")
    private String periodMonthYear;

    private Integer totalMinutes;
    private Integer downtimeMinutes;
    private Double slaTargetPct;
}
