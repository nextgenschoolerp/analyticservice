package com.kvs.analytics.repo;

import com.kvs.analytics.entity.ApiKeyCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiKeyCredentialRepository extends JpaRepository<ApiKeyCredential, UUID> {
    List<ApiKeyCredential> findByTenantId(String tenantId);
    Optional<ApiKeyCredential> findByClientId(String clientId);
}
