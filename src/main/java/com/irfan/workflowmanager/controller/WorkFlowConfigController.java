package com.irfan.workflowmanager.controller;

import com.irfan.workflowmanager.domain.projections.WorkFlowProfile;
import com.irfan.workflowmanager.models.config.WorkFlowRequest;
import com.irfan.workflowmanager.services.config.WorkFlowConfigService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/workflow/config")
@AllArgsConstructor
public class WorkFlowConfigController {

    private final WorkFlowConfigService workFlowConfigService;

    @GetMapping
    public List<WorkFlowProfile> get(HttpServletRequest httpServletRequest) {
        return workFlowConfigService.getAllWorkFlowConfig();
    }

    @PostMapping
    public String create(HttpServletRequest httpServletRequest
            , @Valid @RequestBody WorkFlowRequest workFlowRequest) {
            workFlowConfigService.createWorkFlowConfig(workFlowRequest);
        return "Created Workflow successfully";
    }


    @PutMapping
    public String update(HttpServletRequest httpServletRequest
            , @Valid @RequestBody WorkFlowRequest workFlowRequest) {
        workFlowConfigService.updateWorkFlowConfig(workFlowRequest);
        return "Updated Workflow successfully";
    }


    @DeleteMapping("/{profileId}")
    public String update(HttpServletRequest httpServletRequest
            , @Valid @PathVariable Long profileId) {
        workFlowConfigService.deleteWorkFlowConfig(profileId);
        return "Updated Workflow successfully";
    }
}
