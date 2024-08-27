package com.irfan.workflowmanager.services;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.enums.Activity;

import java.util.List;

public interface WorkflowManagerService {

    void startWorkflow(String transactionNo, String tenantId, String channel, String agentLocationId);

    void updateActivityStatus(String transactionNo, Activity activity, boolean completed);

    List<TransactionWorkFlowActivity> getPendingTransactions(Activity activity);
}
