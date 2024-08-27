package com.irfan.workflowmanager.services.activity;


import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;

public abstract class BaseActivityService implements WorkflowActivityService {

    // Common system activity logic can be implemented here
    protected void performCommonTask(TransactionWorkFlowActivity transactionWorkFlowActivity) {
        // Perform system activity logic here
        // Update transaction workflow or perform any necessary actions
    }

}
