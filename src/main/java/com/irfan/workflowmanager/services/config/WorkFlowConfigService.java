package com.irfan.workflowmanager.services.config;

import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import com.irfan.workflowmanager.models.config.WorkFlowRequest;

import java.util.List;

public interface WorkFlowConfigService {

    void createWorkFlowConfig(WorkFlowRequest workFlowRequest);

    void updateWorkFlowConfig(WorkFlowRequest workFlowRequest);

    WorkFlowProfile getWorkFlowConfig(String tenantId, String channel, String agentLocationId);

    List<WorkFlowProfile> getAllWorkFlowConfig();

    void deleteWorkFlowConfig(Long profileId);
}
