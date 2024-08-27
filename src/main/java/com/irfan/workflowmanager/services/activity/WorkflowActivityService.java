package com.irfan.workflowmanager.services.activity;


import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.enums.ActivityStatus;

public interface WorkflowActivityService {

    ActivityStatus performActivity(TransactionWorkFlowActivity transactionWorkFlowActivity);
}
