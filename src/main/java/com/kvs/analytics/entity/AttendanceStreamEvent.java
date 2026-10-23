package com.kvs.analytics.entity;

import com.kvs.analytics.enums.DeviceDirection;
import com.kvs.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "analytics_attendance_stream_events", indexes = {
        @Index(name = "idx_attstream_tenant", columnList = "tenant_id"),
        @Index(name = "idx_attstream_biometric", columnList = "user_biometric_id"),
        @Index(name = "idx_attstream_punch", columnList = "punch_timestamp")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceStreamEvent extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "device_serial_number", nullable = false, length = 100)
    private String deviceSerialNumber;

    @Column(name = "user_biometric_id", nullable = false, length = 100)
    private String userBiometricId;

    @Column(name = "punch_timestamp", nullable = false)
    private Instant punchTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 10)
    private DeviceDirection direction;

    @Column(name = "verification_type", length = 50)
    @Builder.Default
    private String verificationType = "FINGERPRINT";

    @Column(name = "is_processed", nullable = false)
    @Builder.Default
    private Boolean isProcessed = false;
}
