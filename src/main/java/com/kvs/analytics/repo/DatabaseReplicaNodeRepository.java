package com.kvs.analytics.repo;

import com.kvs.analytics.entity.DatabaseReplicaNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DatabaseReplicaNodeRepository extends JpaRepository<DatabaseReplicaNode, UUID> {
    List<DatabaseReplicaNode> findByTenantId(UUID tenantId);
    Optional<DatabaseReplicaNode> findByTenantIdAndNodeHostname(UUID tenantId, String hostname);
}
