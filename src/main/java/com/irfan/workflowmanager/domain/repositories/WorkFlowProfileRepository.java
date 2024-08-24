package com.irfan.workflowmanager.domain.repositories;

import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkFlowProfileRepository extends JpaRepository<WorkFlowProfile, Long> {

    Optional<WorkFlowProfile> findByTenantIdAndChannelAndAgentLocationId(
            String tenantId, String channel, String agentLocationId);
}
