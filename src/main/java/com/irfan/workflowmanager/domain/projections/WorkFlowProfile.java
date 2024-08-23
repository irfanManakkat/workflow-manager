package com.irfan.workflowmanager.domain.projections;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(indexes = {
        @Index(name = "idx_tenant_channel_agent", columnList = "tenantId, channel, agentLocationId")})
public class WorkFlowProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    private String tenantId;
    private String channel;
    private String agentLocationId;

    private boolean active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "profileId")
    private List<WorkFlowProfileActivity> activities;

}
