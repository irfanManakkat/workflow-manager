package com.irfan.workflowmanager.services.activity.system;

import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.services.activity.WorkflowActivityService;

public abstract class BaseSystemActivityService implements WorkflowActivityService {

    @Override
    public Activity getActivity() {
        return getActivityEnum();
    }

    protected abstract Activity getActivityEnum();

    // Common system activity logic can be implemented here
    protected void performSystemActivity(String transactionNo) {
        // Perform system activity logic here
        // Update transaction workflow or perform any necessary actions
    }

}
