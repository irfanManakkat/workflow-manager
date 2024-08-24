package com.irfan.workflowmanager.services.activity.system;

import com.irfan.workflowmanager.enums.Activity;
import org.springframework.stereotype.Service;

@Service
public class SystemTask1Service extends BaseSystemActivityService {

    @Override
    protected Activity getActivityEnum() {
        return Activity.SYSTEM_TASK;
    }

    @Override
    public void performActivity(String transactionNo) {
        performSystemActivity(transactionNo);
        // Perform additional system task 1 logic here
    }

}
