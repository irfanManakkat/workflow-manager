package com.irfan.workflowmanager.services.activity;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.enums.ActivityStatus;
import org.springframework.stereotype.Service;

@Service
public class Activity2Service extends BaseActivityService {

    @Override
    public ActivityStatus performActivity(TransactionWorkFlowActivity transactionWorkFlowActivity) {
        return ActivityStatus.COMPLETED;
    }

}
