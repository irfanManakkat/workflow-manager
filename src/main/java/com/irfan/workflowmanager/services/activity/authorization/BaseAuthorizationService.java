package com.irfan.workflowmanager.services.activity.authorization;

import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.services.activity.WorkflowActivityService;

public abstract class BaseAuthorizationService implements WorkflowActivityService {

    @Override
    public Activity getActivity() {
        return getActivityEnum();
    }

    protected abstract Activity getActivityEnum();

    // Common authorization logic can be implemented here
    protected void performAuthorization(String transactionNo) {
        // Perform authorization logic here
        // Update transaction workflow or perform any necessary actions
    }

}
