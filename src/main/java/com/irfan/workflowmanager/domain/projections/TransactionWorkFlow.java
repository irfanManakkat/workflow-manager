package com.irfan.workflowmanager.domain.projections;

import com.irfan.workflowmanager.converters.ActivityListConverter;
import com.irfan.workflowmanager.enums.Activity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class TransactionWorkFlow {

    @Id
    private String transactionNo;

    private String profileId;
    private String tenantId;
    private String channel;
    private String agentLocationId;

    @Convert(converter = ActivityListConverter.class)
    @Column(length = 1000)
    private List<Activity> completedActivities;

    @Convert(converter = ActivityListConverter.class)
    @Column(length = 1000)
    private List<Activity> inProgressActivities;

    @Convert(converter = ActivityListConverter.class)
    @Column(length = 1000)
    private List<Activity> pendingActivities;
}
