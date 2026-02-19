package com.mylocalservices.app.repository.service.provider.management.book;



import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.enums.worker.book.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRequestRepository extends JpaRepository<JobRequest, Long> {

    Page<JobRequest> findByWorker_Id(Long workerId, Pageable pageable);

    Page<JobRequest> findByWorker_IdAndStatus(Long workerId, JobStatus status, Pageable pageable);

    // Check if worker has active jobs (REQUESTED or ACCEPTED) during the time period
    boolean existsByWorker_IdAndStatusInAndScheduledAtBetween(
            Long workerId,
            List<JobStatus> statuses,
            LocalDateTime start,
            LocalDateTime end
    );

    // Check if worker has active jobs at a specific time
    boolean existsByWorker_IdAndStatusInAndScheduledAt(
            Long workerId,
            List<JobStatus> statuses,
            LocalDateTime scheduledAt
    );

    boolean existsByCustomer_IdAndWorker_IdAndStatusIn(
            Long customerId,
            Long workerId,
            List<JobStatus> statuses
    );
}
