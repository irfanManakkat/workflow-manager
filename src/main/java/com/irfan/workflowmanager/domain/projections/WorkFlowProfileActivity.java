package com.irfan.workflowmanager.domain.projections;

import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.enums.Operation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class WorkFlowProfileActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private Operation operation;

    private int orderNo;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private Activity activity;

}
