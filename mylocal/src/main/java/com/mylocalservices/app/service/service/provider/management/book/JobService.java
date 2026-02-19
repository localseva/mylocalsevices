package com.mylocalservices.app.service.service.provider.management.book;

import com.mylocalservices.app.dto.service.provider.management.workers.book.CreateJobRequest;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.book.WorkerSchedule;
import com.mylocalservices.app.enums.auth.worker.CancelledBy;
import com.mylocalservices.app.enums.auth.worker.ScheduleStatus;
import com.mylocalservices.app.enums.worker.book.JobStatus;
import com.mylocalservices.app.repository.auth.UserRepository;
import com.mylocalservices.app.repository.service.provider.management.WorkerProfileRepository;
import com.mylocalservices.app.repository.service.provider.management.book.JobRequestRepository;
import com.mylocalservices.app.repository.service.provider.management.book.WorkerScheduleRepository;
import com.mylocalservices.app.service.service.provider.management.notitfication.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRequestRepository jobRepository;
    private final WorkerProfileRepository workerRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final WorkerScheduleRepository scheduleRepository;

    public JobRequest createJob(CreateJobRequest request, String email) {

        if (request.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Scheduled time must be in the future");
        }

        if (request.getScheduledAt() != null) {
            if (request.getStartAt().isBefore(request.getScheduledAt()) ||
                    request.getEndAt().isBefore(request.getScheduledAt())) {
                throw new RuntimeException("Start and End time must be after Scheduled time");
            }
        }
        // 1. Get customer
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get worker
        WorkerProfile worker = workerRepository.findById(request.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        // 3. Check if customer already has a pending or accepted booking with this worker
        boolean alreadyBooked = jobRepository.existsByCustomer_IdAndWorker_IdAndStatusIn(
                customer.getId(),
                worker.getId(),
                Arrays.asList(JobStatus.REQUESTED, JobStatus.ACCEPTED)
        );

        if (alreadyBooked) {
            throw new RuntimeException("You already have a pending or accepted booking with this worker");
        }

        // 4. Conflict check (prevent double-booking for active jobs only)
        List<JobStatus> activeStatuses = List.of(JobStatus.ACCEPTED);

        boolean conflict = jobRepository.existsByWorker_IdAndStatusInAndScheduledAtBetween(
                worker.getId(),
                activeStatuses,
                request.getStartAt(),
                request.getEndAt()
        ) || jobRepository.existsByWorker_IdAndStatusInAndScheduledAt(
                worker.getId(),
                activeStatuses,
                request.getScheduledAt()
        );

        if (conflict) {
            throw new RuntimeException("Worker is already booked during this time slot.");
        }

        // 5. Create JobRequest
        JobRequest job = JobRequest.builder()
                .customer(customer)
                .worker(worker)
                .serviceType(worker.getServiceType().name())
                .description(request.getDescription())
                .address(request.getAddress() != null ? request.getAddress() : "")
                .city(request.getCity() != null ? request.getCity().toLowerCase() : worker.getCity())
                .pincode(request.getPincode() != null ? request.getPincode() : worker.getPincode())
                .scheduledAt(request.getStartAt())
                .startTime(request.getStartAt())
                .endTime(request.getEndAt())
                .status(JobStatus.REQUESTED)
                .build();

        return jobRepository.save(job);
    }

    // Worker Accept Job
    public JobRequest acceptJob(Long jobId, String workerEmail) {
        JobRequest job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Optional: Check if this worker is assigned to this job
        if (!job.getWorker().getUser().getEmail().equals(workerEmail)) {
            throw new RuntimeException("Not authorized to accept this job");
        }

        // Update job status
        job.setStatus(JobStatus.ACCEPTED);

        // Update worker availability for the job time period
        WorkerProfile worker = job.getWorker();

        // Option 1: If you have a specific availability table/entity
        WorkerSchedule schedule = WorkerSchedule.builder()
                .worker(worker)
                .startTime(job.getStartTime())
                .endTime(job.getEndTime())
                .status(ScheduleStatus.BOOKED)
                .jobRequest(job)
                .build();

        scheduleRepository.save(schedule);

        worker.setAvailable(false);
        workerRepository.save(worker);

        JobRequest request = jobRepository.save(job);
//        notificationService.notifyUser(job.getCustomer(), "Your job has been ACCEPTED by " + job.getWorker().getUser().getName());
        return request;
    }

    // Worker Reject Job
    public JobRequest rejectJob(Long jobId, String workerEmail) {
        JobRequest job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getWorker().getUser().getEmail().equals(workerEmail)) {
            throw new RuntimeException("Not authorized to reject this job");
        }

        job.setStatus(JobStatus.REJECTED);
//        notificationService.notifyUser(job.getCustomer(), "Your job has been REJECTED by " + job.getWorker().getUser().getName());
        return jobRepository.save(job);
    }

    // Worker Calendar
    public Page<JobRequest> getWorkerJobs(Long workerId, JobStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledAt").ascending());
        if (status != null) {
            return jobRepository.findByWorker_IdAndStatus(workerId, status, pageable);
        }
        return jobRepository.findByWorker_Id(workerId, pageable);
    }

    // Complete Job
    public JobRequest completeJob(Long jobId, String workerEmail, Integer rating, String feedback) {
        JobRequest job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getWorker().getUser().getEmail().equals(workerEmail))
            throw new RuntimeException("Not allowed");

        job.setStatus(JobStatus.COMPLETED);
        job.setRating(rating);
        job.setFeedback(feedback);
        job.setCompleted(true);

        // Update worker availability back to true
        WorkerProfile worker = job.getWorker();
        worker.setAvailable(true);
        workerRepository.save(worker);

        // Update the schedule status to COMPLETED
        List<WorkerSchedule> schedules = scheduleRepository.findByWorkerAndJobRequest(worker, job);
        if (!schedules.isEmpty()) {
            for (WorkerSchedule schedule : schedules) {
                schedule.setStatus(ScheduleStatus.COMPLETED);
            }
            scheduleRepository.saveAll(schedules);
        }

        jobRepository.save(job);
//        notificationService.notifyUser(job.getCustomer(),
//                "Your job has been completed by " + job.getWorker().getUser().getName());

        return job;
    }

    public JobRequest cancelJob(Long jobId, String email, String reason) {
        JobRequest job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (job.getStatus() == JobStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel completed job");
        }

        job.setCancelled(true);
        job.setCancellationReason(reason);
        job.setCancelledAt(LocalDateTime.now());

        if (job.getCustomer().getId().equals(user.getId())) {
            job.setCancelledBy(CancelledBy.USER);
        } else if (job.getWorker().getUser().getId().equals(user.getId())) {
            job.setCancelledBy(CancelledBy.WORKER);
        } else {
            throw new RuntimeException("Not authorized to cancel this job");
        }

        job.setStatus(JobStatus.CANCELLED);

        return jobRepository.save(job);
    }
}
