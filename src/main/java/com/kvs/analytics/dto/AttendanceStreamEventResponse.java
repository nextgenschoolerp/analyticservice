package com.kvs.analytics.dto;

import com.kvs.analytics.enums.DeviceDirection;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStreamEventResponse {
    private UUID id;
    private String tenantId;
    private String deviceSerialNumber;
    private String userBiometricId;
    private Instant punchTimestamp;
    private DeviceDirection direction;
    private String verificationType;
    private Boolean isProcessed;
    private Instant createdAt;
}
