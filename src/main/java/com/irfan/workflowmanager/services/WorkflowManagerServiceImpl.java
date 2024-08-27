package com.irfan.workflowmanager.services;

import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import com.irfan.workflowmanager.domain.projections.WorkFlowProfileActivity;
import com.irfan.workflowmanager.domain.repositories.TransactionWorkFlowActivityRepository;
import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.enums.ActivityStatus;
import com.irfan.workflowmanager.services.activity.WorkflowActivityService;
import com.irfan.workflowmanager.services.config.WorkFlowConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class WorkflowManagerServiceImpl implements WorkflowManagerService {

    private final WorkFlowConfigService workFlowConfigService;
    private final TransactionWorkFlowActivityRepository transactionWorkFlowActivityRepository;

    private final Map<String, WorkflowActivityService> activityServices;
    private final TransactionActivityLogService transactionActivityLogService;

    public WorkflowManagerServiceImpl(WorkFlowConfigService workFlowConfigService
            , TransactionWorkFlowActivityRepository transactionWorkFlowActivityRepository
            , Map<String, WorkflowActivityService> activityServices
            , TransactionActivityLogService transactionActivityLogService) {
        this.workFlowConfigService = workFlowConfigService;
        this.transactionWorkFlowActivityRepository = transactionWorkFlowActivityRepository;

        this.activityServices = activityServices;
        this.transactionActivityLogService = transactionActivityLogService;
    }

    @Override
    public void startWorkflow(String transactionNo, String tenantId, String channel, String agentLocationId) {
        WorkFlowProfile profile = workFlowConfigService.getWorkFlowConfig(tenantId, channel, agentLocationId);

        log.info("Starting work flow activities {}", transactionNo);

        List<WorkFlowProfileActivity> activities = profile.getActivities().stream()
                .sorted(Comparator.comparing(WorkFlowProfileActivity::getOrderNo))
                .toList();

        if(activities.isEmpty())
            return;

        List<TransactionWorkFlowActivity> transactionWorkFlowActivities = activities.stream()
                .map(activity-> buiildTransactionWorkFlowActivity(tenantId, channel, agentLocationId, transactionNo, activity))
                .toList();

        transactionWorkFlowActivityRepository.saveAll(transactionWorkFlowActivities);
        executeWorkFlow(transactionWorkFlowActivities);
    }

    private static TransactionWorkFlowActivity buiildTransactionWorkFlowActivity(String tenantId, String channel
            , String agentLocationId, String transactionNo, WorkFlowProfileActivity workFlowProfileActivity)
    {
        TransactionWorkFlowActivity transactionWorkFlowActivity = new TransactionWorkFlowActivity();
        transactionWorkFlowActivity.setTransactionNo(transactionNo);
        transactionWorkFlowActivity.setActivity(workFlowProfileActivity.getActivity());
        transactionWorkFlowActivity.setActivityId(workFlowProfileActivity.getActivityId());
        transactionWorkFlowActivity.setActivityStatus(ActivityStatus.PENDING);
        transactionWorkFlowActivity.setOrderNo(workFlowProfileActivity.getOrderNo());
        transactionWorkFlowActivity.setTenantId(tenantId);
        transactionWorkFlowActivity.setChannel(channel);
        transactionWorkFlowActivity.setAgentLocationId(agentLocationId);
        return transactionWorkFlowActivity;
    }

    public void executeWorkFlow(List<TransactionWorkFlowActivity> transactionWorkFlowActivities) {
        log.info("Executing work flow activities {}", transactionWorkFlowActivities);

        List<TransactionWorkFlowActivity> pendingActivities = new ArrayList<>(transactionWorkFlowActivities.stream()
                .filter(activity -> activity.getActivityStatus().equals(ActivityStatus.PENDING))
                .sorted(Comparator.comparing(TransactionWorkFlowActivity::getOrderNo))
                .toList());

        if (pendingActivities.isEmpty())
            return;

        TransactionWorkFlowActivity firstActivity = pendingActivities.getFirst();
        List<TransactionWorkFlowActivity> parallelActivities = pendingActivities.stream()
                .filter(activity -> activity.getOrderNo()==firstActivity.getOrderNo())
                .toList();

        AtomicReference<Integer> completedActivitySize = new AtomicReference<>(0);
        parallelActivities.parallelStream().forEachOrdered(activity-> {
            ActivityStatus activityStatus = executeActivity(activity);
            if(activityStatus.equals(ActivityStatus.COMPLETED))
                completedActivitySize.getAndSet(completedActivitySize.get() + 1);
        });

        if(parallelActivities.size() == completedActivitySize.get())
        {
            pendingActivities.removeAll(parallelActivities);
            executeWorkFlow(pendingActivities);
        }
    }


    private ActivityStatus executeActivity(TransactionWorkFlowActivity transactionWorkFlowActivity) {
        // Execute the activity
        log.info("Executing activity {}", transactionWorkFlowActivity);
        transactionActivityLogService.saveTransactionWorkFlowActivity(transactionWorkFlowActivity, ActivityStatus.IN_PROGRESS);

        ActivityStatus activityStatus = activityServices.get(transactionWorkFlowActivity.getActivity().getClassName())
                .performActivity(transactionWorkFlowActivity);

        if (ActivityStatus.COMPLETED.equals(activityStatus)) {
            transactionActivityLogService.saveTransactionWorkFlowActivity(transactionWorkFlowActivity, activityStatus);
            transactionWorkFlowActivityRepository.delete(transactionWorkFlowActivity);
        }
        else
        {
            transactionWorkFlowActivity.setActivityStatus(activityStatus);
            transactionWorkFlowActivityRepository.save(transactionWorkFlowActivity);
        }

        return activityStatus;
    }

    @Override
    public void updateActivityStatus(String transactionNo, Activity activity, boolean completed) {

        log.debug("Updating activity status transactionNo:{}, activity:{}, completed:{}"
                , transactionNo, activity, completed);

        List<TransactionWorkFlowActivity> transactionWorkFlowActivities
                = transactionWorkFlowActivityRepository.findByTransactionNo(transactionNo);

        if (transactionWorkFlowActivities.isEmpty())
            throw new RuntimeException("No Pending Transaction Activity");

        TransactionWorkFlowActivity transactionWorkFlowActivity = transactionWorkFlowActivities.stream()
                .filter(transactionActivity-> transactionActivity.getActivity().equals(activity))
                .findFirst().orElseThrow(()-> new RuntimeException("No Pending Transaction Activity"));

        transactionWorkFlowActivities.remove(transactionWorkFlowActivity);

        if (completed) {
            transactionActivityLogService.saveTransactionWorkFlowActivity(transactionWorkFlowActivity, ActivityStatus.COMPLETED);

            transactionWorkFlowActivityRepository.delete(transactionWorkFlowActivity);
            //update in Transaction ActivityLog
            List<TransactionWorkFlowActivity> parallelActivities = transactionWorkFlowActivities.stream()
                    .filter(activity1 -> activity1.getOrderNo()==transactionWorkFlowActivity.getOrderNo())
                    .toList();

            if(parallelActivities.isEmpty())
            {
                executeWorkFlow(transactionWorkFlowActivities);
            }

        } else {
            transactionActivityLogService.saveTransactionWorkFlowActivity(transactionWorkFlowActivity, ActivityStatus.REJECTED);
            // Handle activity failure or other status updates
        }
    }

    @Override
    public List<TransactionWorkFlowActivity> getPendingTransactions(Activity activity) {
        return transactionWorkFlowActivityRepository.findByActivity(activity);
    }
}
