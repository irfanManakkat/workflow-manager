package com.irfan.workflowmanager.services;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.enums.ActivityStatus;

public interface TransactionActivityLogService {

    void saveTransactionWorkFlowActivity(TransactionWorkFlowActivity transactionWorkFlowActivity
            , ActivityStatus activityStatus);
}
