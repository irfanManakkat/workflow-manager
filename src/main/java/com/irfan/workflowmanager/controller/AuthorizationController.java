package com.irfan.workflowmanager.controller;

import com.irfan.workflowmanager.enums.Activity;
import com.irfan.workflowmanager.models.authorization.AuthorizationDto;
import com.irfan.workflowmanager.services.activity.authorization.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authorization")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("{activity}/pending")
    public List<AuthorizationDto> getPendingAuthorization(HttpServletRequest httpServletRequest
            , @Valid @PathVariable @NotNull(message = "activity is mandatory") Activity activity) {

        return authorizationService.getPendingAuthorization(activity);
    }


    @PostMapping("{activity}/authorize/{transactionNo}")
    public String authorize(HttpServletRequest httpServletRequest
            , @Valid @PathVariable @NotNull(message = "activity is mandatory") Activity activity
            , @Valid @PathVariable @NotNull(message = "transactionNo is mandatory") String transactionNo) {

        authorizationService.authorize(activity, transactionNo);
        return "Authorized successfully";
    }

    @PostMapping("{activity}/deny/{transactionNo}")
    public String deny(HttpServletRequest httpServletRequest
            , @Valid @PathVariable @NotNull(message = "activity is mandatory") Activity activity
            , @Valid @PathVariable @NotNull(message = "transactionNo is mandatory") String transactionNo) {

        authorizationService.deny(activity, transactionNo);
        return "Authorized successfully";
    }


}
