package com.irfan.workflowmanager.domain.repositories;

import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkFlowProfileRepository extends JpaRepository<WorkFlowProfile, Long> {
}
