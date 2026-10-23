package com.kvs.analytics.dto;

import com.kvs.analytics.enums.DeviceDirection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStreamEventRequest {

    @NotNull(message = "tenantId is mandatory")
    private UUID tenantId;

    @NotBlank(message = "deviceSerialNumber cannot be blank")
    private String deviceSerialNumber;

    @NotBlank(message = "userBiometricId cannot be blank")
    private String userBiometricId;

    @NotNull(message = "punchTimestamp is mandatory")
    private Instant punchTimestamp;

    @NotNull(message = "direction is mandatory")
    private DeviceDirection direction;

    private String verificationType;
}
