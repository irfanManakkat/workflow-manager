package com.irfan.workflowmanager.services;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlow;
import com.irfan.workflowmanager.enums.Activity;

public interface WorkflowManagerService {

    void startWorkflow(String transactionNo, String tenantId, String channel, String agentLocationId);

    void executeWorkFlow(TransactionWorkFlow transactionWorkFlow);

    void updateActivityStatus(String transactionNo, Activity activity, boolean completed);
}
