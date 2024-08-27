package com.irfan.workflowmanager.controller;

import com.irfan.workflowmanager.services.WorkflowManagerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/workflow")
@AllArgsConstructor
public class WorkFlowController {

    private final WorkflowManagerService workflowManagerService;


    @PostMapping("/start/{transactionNo}")
    public String startWorkFlow(HttpServletRequest httpServletRequest
            , @Valid @PathVariable @NotNull(message = "activity is mandatory") String transactionNo) {
        workflowManagerService.startWorkflow(transactionNo, "784100", "WEB", "784101");
        return "Started Workflow successfully";
    }
}
