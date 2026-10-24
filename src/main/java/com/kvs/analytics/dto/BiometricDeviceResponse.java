package com.kvs.analytics.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiometricDeviceResponse {
    private UUID id;
    private String tenantId;
    private String deviceSerialNumber;
    private String deviceName;
    private String ipAddress;
    private Integer portNumber;
    private String location;
    private String deviceModel;
    private Boolean isOnline;
    private Instant lastHeartbeatAt;
    private Instant createdAt;
}
