package com.mylocalservices.app.controller.service.provider.management.book;

import com.mylocalservices.app.dto.service.provider.management.JobCompleteRequest;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerProfileResponse;
import com.mylocalservices.app.dto.service.provider.management.book.CreateJobRequest;
import com.mylocalservices.app.dto.service.provider.management.book.JobActionRequest;
import com.mylocalservices.app.dto.service.provider.management.book.JobRequestDTO;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.enums.worker.book.JobStatus;
import com.mylocalservices.app.service.service.provider.management.WorkerProfileService;
import com.mylocalservices.app.service.service.provider.management.book.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final WorkerProfileService workerProfileService;

    @PostMapping("/create")
    public ResponseEntity<JobRequestDTO> createJob(
            @RequestBody CreateJobRequest request,
            Authentication authentication
    ) {
        JobRequest job = jobService.createJob(request, authentication.getName());
        return ResponseEntity.ok(JobRequestDTO.fromEntity(job));
    }

    @PostMapping("/accept")
    public ResponseEntity<JobRequestDTO> acceptJob(@RequestBody JobActionRequest request, Authentication auth) {
        // auth.getName() = logged-in worker email
        JobRequest job = jobService.acceptJob(request.getJobId(), auth.getName());
        return ResponseEntity.ok(JobRequestDTO.fromEntity(job));
    }

    @PostMapping("/reject")
    public ResponseEntity<JobRequestDTO> rejectJob(@RequestBody JobActionRequest request, Authentication auth) {
        JobRequest job = jobService.rejectJob(request.getJobId(), auth.getName());
        return ResponseEntity.ok(JobRequestDTO.fromEntity(job));
    }

    // Worker Calendar / Job List
    //It returns all accepted jobs (bookings) of a worker in a time range so the worker (or system) can see:
    //✅ Upcoming jobs
    //✅ Busy time slots
    //✅ Free time slots
    //✅ Prevent double booking
    //✅ Show calendar UI
    @GetMapping("/calendar")
    public ResponseEntity<Page<JobRequestDTO>> getCalendar(
            @RequestParam(required = false) JobStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication auth) {

        WorkerProfileResponse worker = workerProfileService.getMyProfile(auth.getName());
        Page<JobRequest> jobsPage = jobService.getWorkerJobs(worker.getId(), status, page, size);
        
        // Convert Page<JobRequest> to Page<JobRequestDTO>
        Page<JobRequestDTO> dtoPage = new PageImpl<>(
                jobsPage.getContent().stream()
                        .map(JobRequestDTO::fromEntity)
                        .collect(Collectors.toList()),
                jobsPage.getPageable(),
                jobsPage.getTotalElements()
        );
        
        return ResponseEntity.ok(dtoPage);
    }

    // Complete Job + Feedback
    @PostMapping("/complete")
    public ResponseEntity<JobRequestDTO> completeJob(@RequestBody JobCompleteRequest request, Authentication auth) {
        JobRequest job = jobService.completeJob(
                request.getJobId(),
                auth.getName(),
                request.getRating(),
                request.getFeedback()
        );
        return ResponseEntity.ok(JobRequestDTO.fromEntity(job));
    }
}
