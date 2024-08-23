package com.irfan.workflowmanager.domain.projections;

import com.irfan.workflowmanager.enums.Activity;
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

    private String operation;

    private Integer order;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private Activity activity;

    private String reference;
    private String condition;

}
