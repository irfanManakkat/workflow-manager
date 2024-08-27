package com.irfan.workflowmanager.services.activity.authorization;

import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.models.authorization.AuthorizationDto;

import java.util.List;


public interface AuthorizationService {


    List<AuthorizationDto> getPendingAuthorization(Activity activity);

    void authorize(Activity activity, String transactionNo);

    void deny(Activity activity, String transactionNo);

}
