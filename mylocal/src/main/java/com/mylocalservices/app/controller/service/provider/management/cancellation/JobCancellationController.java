package com.mylocalservices.app.controller.service.provider.management.cancellation;

import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.service.service.provider.management.book.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobCancellationController {

    private final JobService jobService;

    /**
     * Cancel a job (by customer or worker)
     */
    @PostMapping("/{jobId}/cancel")
    public JobRequest cancelJob(
            @PathVariable Long jobId,
            @RequestParam String reason,
            Authentication auth) {

        // auth.getName() = logged-in user's email
        return jobService.cancelJob(jobId, auth.getName(), reason);
    }
}
