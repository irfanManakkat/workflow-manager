package com.irfan.workflowmanager.services.activity.authorization;

import com.irfan.workflowmanager.enums.Activity;
import org.springframework.stereotype.Service;

@Service
public class PaymentAuthorizationService extends BaseAuthorizationService{

    @Override
    protected Activity getActivityEnum() {
        return Activity.PAYMENT_AUTHORIZATION;
    }

    @Override
    public void performActivity(String transactionNo) {
        performAuthorization(transactionNo);
        // Perform additional payment authorization logic here
    }
}
