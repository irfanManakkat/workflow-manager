package com.irfan.workflowmanager.domain.repositories;

import com.irfan.workflowmanager.domain.projections.TransactionActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionActivityLogRepository extends JpaRepository<TransactionActivityLog, String> {
}
