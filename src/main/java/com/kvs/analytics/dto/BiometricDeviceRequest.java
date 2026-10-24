package com.kvs.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiometricDeviceRequest {

    @NotNull(message = "tenantId is mandatory")
    private String tenantId;

    @NotBlank(message = "deviceSerialNumber cannot be blank")
    private String deviceSerialNumber;

    @NotBlank(message = "deviceName cannot be blank")
    private String deviceName;

    @NotBlank(message = "ipAddress cannot be blank")
    private String ipAddress;

    private Integer portNumber;
    private String location;
    private String deviceModel;
}
