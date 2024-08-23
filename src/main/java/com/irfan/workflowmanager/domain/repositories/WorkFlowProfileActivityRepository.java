package com.irfan.workflowmanager.domain.repositories;


import com.irfan.workflowmanager.domain.projections.WorkFlowProfileActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkFlowProfileActivityRepository extends JpaRepository<WorkFlowProfileActivity, Long> {
}
