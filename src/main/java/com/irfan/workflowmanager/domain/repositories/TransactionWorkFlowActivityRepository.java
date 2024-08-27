package com.irfan.workflowmanager.domain.repositories;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.domain.projections.TransactionWorkflowActivityId;
import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionWorkFlowActivityRepository extends JpaRepository<TransactionWorkFlowActivity, TransactionWorkflowActivityId> {

    List<TransactionWorkFlowActivity> findByActivityStatus(ActivityStatus activityStatus);
    List<TransactionWorkFlowActivity> findByActivity(Activity activity);

    List<TransactionWorkFlowActivity> findByTransactionNo(String transactionNo);
}
