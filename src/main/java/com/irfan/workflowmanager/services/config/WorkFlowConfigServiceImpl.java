package com.irfan.workflowmanager.services.config;

import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import com.irfan.workflowmanager.domain.repositories.WorkFlowProfileRepository;
import com.irfan.workflowmanager.models.config.WorkFlowRequest;
import com.irfan.workflowmanager.utils.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkFlowConfigServiceImpl implements WorkFlowConfigService {

    private final WorkFlowProfileRepository workFlowProfileRepository;

    public WorkFlowConfigServiceImpl(WorkFlowProfileRepository workFlowProfileRepository) {
        this.workFlowProfileRepository = workFlowProfileRepository;
    }

    @Override
    public void createWorkFlowConfig(WorkFlowRequest workFlowRequest) {
        Optional<WorkFlowProfile> optionalWorkFlowProfile = workFlowProfileRepository
                .findByTenantIdAndChannelAndAgentLocationId(workFlowRequest.getTenantId(), workFlowRequest.getChannel()
                        , workFlowRequest.getAgentLocationId());

        if (optionalWorkFlowProfile.isPresent())
            throw new RuntimeException("WorkFlow Config already Exists");

        WorkFlowProfile workFlowProfile = buildWorkFlowProfile(workFlowRequest);
        workFlowProfileRepository.save(workFlowProfile);
    }

    private static WorkFlowProfile buildWorkFlowProfile(WorkFlowRequest workFlowRequest) {
        return MapperUtils.toObject(workFlowRequest, WorkFlowProfile.class);
    }

    @Override
    public void updateWorkFlowConfig(WorkFlowRequest workFlowRequest) {
        WorkFlowProfile workFlowProfile = getWorkFlowConfig(workFlowRequest.getTenantId(), workFlowRequest.getChannel()
                , workFlowRequest.getAgentLocationId());

        WorkFlowProfile workFlowProfileRequest = buildWorkFlowProfile(workFlowRequest);
        workFlowProfile.setActivities(workFlowProfileRequest.getActivities());
        workFlowProfile.setActive(workFlowProfileRequest.isActive());
        workFlowProfileRepository.save(workFlowProfile);
    }

    @Override
    public WorkFlowProfile getWorkFlowConfig(String tenantId, String channel, String agentLocationId) {
        return workFlowProfileRepository.findByTenantIdAndChannelAndAgentLocationId(tenantId, channel, agentLocationId)
                .orElseThrow(() -> new RuntimeException("WorkFlow Config not found"));
    }

    @Override
    public List<WorkFlowProfile> getAllWorkFlowConfig() {
        return workFlowProfileRepository.findAll();
    }

    @Override
    public void deleteWorkFlowConfig(Long profileId) {
        Optional<WorkFlowProfile> optionalWorkFlowProfile = workFlowProfileRepository.findById(profileId);
        if (optionalWorkFlowProfile.isEmpty())
            throw new RuntimeException("WorkFlow Config not found");

        workFlowProfileRepository.delete(optionalWorkFlowProfile.get());
    }
}
