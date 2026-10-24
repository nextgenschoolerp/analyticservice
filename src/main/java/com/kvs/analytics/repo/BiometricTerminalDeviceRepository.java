package com.kvs.analytics.repo;

import com.kvs.analytics.entity.BiometricTerminalDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BiometricTerminalDeviceRepository extends JpaRepository<BiometricTerminalDevice, UUID> {
    List<BiometricTerminalDevice> findByTenantId(String tenantId);
    Optional<BiometricTerminalDevice> findByDeviceSerialNumber(String serialNumber);
}
