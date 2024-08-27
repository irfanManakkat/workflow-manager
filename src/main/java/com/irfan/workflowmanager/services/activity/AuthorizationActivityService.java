package com.irfan.workflowmanager.services.activity;


import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.enums.ActivityStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationActivityService extends BaseActivityService {


    @Override
    public ActivityStatus performActivity(TransactionWorkFlowActivity transactionWorkFlowActivity) {
        performCommonTask(transactionWorkFlowActivity);
        // Perform additional system task 1 logic here

        return ActivityStatus.IN_PROGRESS;
    }

}
