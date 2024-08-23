package com.irfan.workflowmanager.domain.repositories;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionWorkFlowRepository extends JpaRepository<TransactionWorkFlow, String> {
}
