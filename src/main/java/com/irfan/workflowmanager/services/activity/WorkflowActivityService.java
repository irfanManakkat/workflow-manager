package com.irfan.workflowmanager.services.activity;

import com.irfan.workflowmanager.enums.Activity;

public interface WorkflowActivityService {

    Activity getActivity();

    void performActivity(String transactionNo);
}
