package com.irfan.workflowmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Activity {

    ACTIVITY_1("System Task",  "activity1Service"),
    ACTIVITY_2("System Task",  "activity2Service"),
    PAYMENT_AUTHORIZATION("Payment Authorization", "authorizationActivityService"),
    FUND_AUTHORIZATION("Fund Authorization", "authorizationActivityService"),

    ;

    private final String description;
    private final String className;
}
