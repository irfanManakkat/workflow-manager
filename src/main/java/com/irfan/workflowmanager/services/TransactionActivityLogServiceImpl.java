package com.irfan.workflowmanager.services;

import com.irfan.workflowmanager.domain.projections.TransactionActivityLog;
import com.irfan.workflowmanager.domain.projections.TransactionWorkFlowActivity;
import com.irfan.workflowmanager.domain.repositories.TransactionActivityLogRepository;
import com.irfan.workflowmanager.enums.ActivityStatus;
import org.springframework.stereotype.Service;


@Service
public class TransactionActivityLogServiceImpl implements TransactionActivityLogService {

    private final TransactionActivityLogRepository transactionActivityLogRepository;

    public TransactionActivityLogServiceImpl(TransactionActivityLogRepository transactionActivityLogRepository) {
        this.transactionActivityLogRepository = transactionActivityLogRepository;
    }

    @Override
    public void saveTransactionWorkFlowActivity(TransactionWorkFlowActivity transactionWorkFlowActivity, ActivityStatus activityStatus) {
        TransactionActivityLog transactionActivityLog = new TransactionActivityLog();
        transactionActivityLog.setTransactionNo(transactionWorkFlowActivity.getTransactionNo());
        transactionActivityLog.setActivity(transactionWorkFlowActivity.getActivity());
        transactionActivityLog.setActivityId(transactionWorkFlowActivity.getActivityId());
        transactionActivityLog.setOperation(transactionWorkFlowActivity.getActivity().getDescription()
                + " "+ activityStatus.name());
        transactionActivityLogRepository.save(transactionActivityLog);
    }
}
