package com.irfan.workflowmanager.services.activity.system;

import com.irfan.workflowmanager.enums.Activity;
import org.springframework.stereotype.Service;

@Service
public class SystemTask2Service extends BaseSystemActivityService {

    @Override
    protected Activity getActivityEnum() {
        return Activity.SYSTEM_TASK2;
    }

    @Override
    public void performActivity(String transactionNo) {
        performSystemActivity(transactionNo);
        // Perform additional system task 2 logic here
    }
}
