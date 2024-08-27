package com.irfan.workflowmanager.domain.projections;

import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.enums.ActivityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@IdClass(TransactionWorkflowActivityId.class)
public class TransactionWorkFlowActivity {

    @Id
    private String transactionNo;

    @Id
    private Long activityId;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private Activity activity;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    private int orderNo;

    private String tenantId;
    private String channel;
    private String agentLocationId;

}
