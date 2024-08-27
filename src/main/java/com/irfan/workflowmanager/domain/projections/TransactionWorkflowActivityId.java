package com.irfan.workflowmanager.domain.projections;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionWorkflowActivityId implements Serializable {

    private String transactionNo;
    private Long activityId;
}
