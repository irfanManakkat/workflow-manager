package com.irfan.workflowmanager.services.activity.authorization;

import com.irfan.workflowmanager.enums.Activity;
import org.springframework.stereotype.Service;

@Service
public class BICAuthorizationService extends BaseAuthorizationService{

    @Override
    protected Activity getActivityEnum() {
        return Activity.FUND_AUTHORIZATION;
    }

    @Override
    public void performActivity(String transactionNo) {
        performAuthorization(transactionNo);
        // Perform additional fund authorization logic here
    }

}

