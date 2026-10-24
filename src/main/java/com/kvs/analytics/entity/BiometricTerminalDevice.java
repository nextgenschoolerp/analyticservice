package com.kvs.analytics.entity;

import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_biometric_devices", indexes = {
        @Index(name = "idx_biometric_tenant", columnList = "tenant_id"),
        @Index(name = "idx_biometric_serial", columnList = "device_serial_number", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiometricTerminalDevice extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "device_serial_number", nullable = false, unique = true, length = 100)
    private String deviceSerialNumber;

    @Column(name = "device_name", nullable = false, length = 150)
    private String deviceName;

    @Column(name = "ip_address", nullable = false, length = 50)
    private String ipAddress;

    @Column(name = "port_number", nullable = false)
    @Builder.Default
    private Integer portNumber = 4370;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "device_model", length = 100)
    private String deviceModel;

    @Column(name = "is_online", nullable = false)
    @Builder.Default
    private Boolean isOnline = true;

    @Column(name = "last_heartbeat_at")
    private Instant lastHeartbeatAt;
}
