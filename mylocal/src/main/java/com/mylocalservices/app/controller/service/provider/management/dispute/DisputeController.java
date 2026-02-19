package com.mylocalservices.app.controller.service.provider.management.dispute;

import com.mylocalservices.app.dto.service.provider.management.workers.dispute.RaiseDisputeRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.dispute.Dispute;
import com.mylocalservices.app.service.service.provider.management.dispute.DisputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disputes")
@RequiredArgsConstructor
public class DisputeController {

    private final DisputeService disputeService;

    /**
     * Raise a dispute for a job
     */
    @PostMapping("/raise")
    public Dispute raiseDispute(
            @RequestBody RaiseDisputeRequest request,
            Authentication auth) {

        return disputeService.raiseDispute(request, auth.getName());
    }

    /**
     * Get disputes for a job
     */
    @GetMapping("/job/{jobId}")
    public List<Dispute> getDisputesByJob(@PathVariable Long jobId) {
        return disputeService.getDisputesByJob(jobId);
    }

    /**
     * Get disputes raised by logged-in user
     */
    @GetMapping("/my")
    public List<Dispute> getMyDisputes(Authentication auth) {
        return disputeService.getDisputesByUser(auth.getName());
    }

    @PostMapping("/close/{disputeId}")
    public Dispute closeDispute(
            @PathVariable Long disputeId,
            Authentication auth) {

        return disputeService.closeDispute(disputeId, auth.getName());
    }
}
