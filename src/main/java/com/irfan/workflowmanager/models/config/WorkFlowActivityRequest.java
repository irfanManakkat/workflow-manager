package com.irfan.workflowmanager.models.config;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.enums.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkFlowActivityRequest {

    @NotNull(message = "operation is mandatory")
    private Operation operation;

    @NotNull(message = "orderNo is mandatory")
    @Min(value = 1,message = "orderNo should be greater than 0")
    private Integer orderNo;

    @NotNull(message = "activity is mandatory")
    private Activity activity;

}
