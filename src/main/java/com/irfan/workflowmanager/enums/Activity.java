package com.irfan.workflowmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Activity {

    SYSTEM_TASK("System Task", false, "SystemTaskService"),
    SYSTEM_TASK2("System Task", false, "SystemTask2Service"),
    PAYMENT_AUTHORIZATION("Payment Authorization", true, "PaymentAuthorizationService"),
    FUND_AUTHORIZATION("Fund Authorization", true, "FundAuthorizationService"),

    ;

    private final String description;
    private final boolean authorization;
    private final String className;
}
