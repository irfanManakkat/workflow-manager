package com.irfan.workflowmanager.models.config;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkFlowRequest {

    @Size(max = 10, message="tenantId size should be lower or equal to 10")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "tenantId should be Alphanumeric")
    private String tenantId;

    @Size(max = 30, message="channel size should be lower or equal to 30")
    @Pattern(regexp = "^[a-zA-Z0-9_\\-]*$", message = "channel should contain only [Alphanumeric,_,-]")
    private String channel;

    @Size(max = 10, message="agentLocationId size should be lower or equal to 10")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "agentLocationId should be Alphanumeric")
    private String agentLocationId;

    @NotNull(message = "active is mandatory")
    private Boolean active;

    @Valid
    @NotEmpty(message = "activities cannot be empty")
    private List<WorkFlowActivityRequest> activities;
}
