package com.irfan.workflowmanager.services.impl;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlow;
import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import com.irfan.workflowmanager.domain.projections.WorkFlowProfileActivity;
import com.irfan.workflowmanager.domain.repositories.TransactionWorkFlowRepository;
import com.irfan.workflowmanager.domain.repositories.WorkFlowProfileRepository;
import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.services.WorkflowManagerService;
import com.irfan.workflowmanager.services.activity.WorkflowActivityService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkflowManagerServiceImpl implements WorkflowManagerService {

    private final WorkFlowProfileRepository workFlowProfileRepository;
    private final TransactionWorkFlowRepository transactionWorkFlowRepository;
    private final Map<String, WorkflowActivityService> activityServices;

    public WorkflowManagerServiceImpl(WorkFlowProfileRepository workFlowProfileRepository
            , TransactionWorkFlowRepository transactionWorkFlowRepository
            , Map<String, WorkflowActivityService> activityServices) {
        this.workFlowProfileRepository = workFlowProfileRepository;
        this.transactionWorkFlowRepository = transactionWorkFlowRepository;
        this.activityServices = activityServices;
    }

    @Override
    public void startWorkflow(String transactionNo, String tenantId, String channel, String agentLocationId) {
        WorkFlowProfile profile = workFlowProfileRepository.findByTenantIdAndChannelAndAgentLocationId(tenantId, channel, agentLocationId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<WorkFlowProfileActivity> activities = profile.getActivities().stream()
                .sorted(Comparator.comparing(WorkFlowProfileActivity::getOrder))
                .toList();

        TransactionWorkFlow transactionWorkFlow = new TransactionWorkFlow();
        transactionWorkFlow.setTransactionNo(transactionNo);
        transactionWorkFlow.setCompletedActivities(new ArrayList<>());
        transactionWorkFlow.setInProgressActivities(new ArrayList<>());
        transactionWorkFlow.setPendingActivities(activities.stream()
                .map(WorkFlowProfileActivity::getActivity)
                .toList());

        transactionWorkFlowRepository.save(transactionWorkFlow);
        executeWorkFlow(transactionWorkFlow);
    }

    @Override
    public void executeWorkFlow(TransactionWorkFlow transactionWorkFlow) {
        Optional<WorkFlowProfile> optionalProfile = workFlowProfileRepository.findById(transactionWorkFlow.getProfileId());

        if (optionalProfile.isEmpty())
            throw new RuntimeException("Profile not found");

        executeWorkFlow(transactionWorkFlow, optionalProfile.get().getActivities());
    }

    private void executeWorkFlow(TransactionWorkFlow transactionWorkFlow, List<WorkFlowProfileActivity> activities) {
        List<Activity> pendingActivities = transactionWorkFlow.getPendingActivities();
        if (pendingActivities.isEmpty())
            return;

        Activity firstActivity = pendingActivities.getFirst();
        List<Activity> parallelActivities = transactionWorkFlow.getPendingActivities().stream()
                .filter(activity -> getOrder(activity, activities).equals(getOrder(firstActivity, activities)))
                .toList();

        parallelActivities.parallelStream().forEach(
                activity -> executeActivity(transactionWorkFlow.getTransactionNo(), activity));
    }

    private void executeActivity(String transactionNo, Activity activity) {
        // Execute the activity
        activityServices.get(activity.getClassName()).performActivity(transactionNo);
    }

    private Integer getOrder(Activity activity, List<WorkFlowProfileActivity> profileActivities) {
        return profileActivities.stream()
                .filter(profileActivity -> profileActivity.getActivity().equals(activity))
                .map(WorkFlowProfileActivity::getOrder)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found for activity: " + activity.name()));
    }

    @Override
    public void updateActivityStatus(String transactionNo, Activity activity, boolean completed) {
        Optional<TransactionWorkFlow> optionalTransactionWorkFlow = transactionWorkFlowRepository.findById(transactionNo);

        if (optionalTransactionWorkFlow.isEmpty())
            throw new RuntimeException("Transaction workflow not found");
        TransactionWorkFlow transactionWorkFlow = optionalTransactionWorkFlow.get();
        if (completed) {
            transactionWorkFlow.getPendingActivities().remove(activity);
            transactionWorkFlow.getCompletedActivities().add(activity);
            transactionWorkFlowRepository.save(transactionWorkFlow);
            // Continue the workflow if there are pending activities
            executeWorkFlow(transactionWorkFlow);

        } else {

            // Handle activity failure or other status updates
        }
    }
}
