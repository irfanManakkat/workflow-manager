package com.irfan.workflowmanager.domain.projections;

import com.irfan.workflowmanager.enums.Activity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
public class TransactionActivityLog {

    @Id
    private String transactionNo;

    private Long activityId;

    private String operation;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private Activity activity;

    private String userId;

    private OffsetDateTime activityTime;
}
