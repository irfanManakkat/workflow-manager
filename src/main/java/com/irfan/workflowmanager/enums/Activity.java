package com.irfan.workflowmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Activity {

    SYSTEM_TASK("System Task", false),
    PAYMENT_AUTHORIZATION("Payment Authorization", true),
    FUND_AUTHORIZATION("Fund Authorization", true)

    ;

    private final String description;
    private final boolean authorization;
}
