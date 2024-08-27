package com.irfan.workflowmanager.services.activity.authorization;


import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.models.authorization.AuthorizationDto;
import com.irfan.workflowmanager.services.WorkflowManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final WorkflowManagerService workflowManagerService;

    public AuthorizationServiceImpl(WorkflowManagerService workflowManagerService) {
        this.workflowManagerService = workflowManagerService;
    }

    @Override
    public List<AuthorizationDto> getPendingAuthorization(Activity activity) {
        return workflowManagerService.getPendingTransactions(activity).stream()
                .map(transaction -> new AuthorizationDto(transaction.getTransactionNo(), transaction.getTenantId()
                        , transaction.getChannel(), transaction.getAgentLocationId()))
                .toList();
    }

    @Override
    public void authorize(Activity activity, String transactionNo) {
        workflowManagerService.updateActivityStatus(transactionNo, activity, true);
    }

    @Override
    public void deny(Activity activity, String transactionNo) {
        workflowManagerService.updateActivityStatus(transactionNo, activity, false);
    }
}

