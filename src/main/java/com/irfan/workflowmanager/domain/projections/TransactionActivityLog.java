package com.irfan.workflowmanager.domain.projections;

import com.irfan.workflowmanager.enums.Activity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@Setter
@ToString
public class TransactionActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionActivityId;

    private String transactionNo;

    private Long activityId;

    private String operation;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private Activity activity;

    private String userId="User1";

    private OffsetDateTime activityTime= OffsetDateTime.now(ZoneOffset.UTC);
}
