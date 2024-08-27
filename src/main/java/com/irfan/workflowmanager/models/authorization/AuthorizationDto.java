package com.irfan.workflowmanager.models.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthorizationDto {

    private String transactionNo;
    private String tenantId;
    private String channel;
    private String agentLocationId;
}
