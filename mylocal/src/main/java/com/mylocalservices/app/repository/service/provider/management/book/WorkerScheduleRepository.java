package com.mylocalservices.app.repository.service.provider.management.book;

import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.book.WorkerSchedule;
import com.mylocalservices.app.enums.auth.worker.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkerScheduleRepository
        extends JpaRepository<WorkerSchedule, Long> {

    // Check if worker has FREE slot overlapping requested time
    boolean existsByWorker_IdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndStatus(
            Long workerId,
            LocalDateTime end,
            LocalDateTime start,
            ScheduleStatus status
    );

    // Get available slots for worker
    List<WorkerSchedule> findByWorker_IdAndStatusAndStartTimeAfterOrderByStartTime(
            Long workerId,
            ScheduleStatus status,
            LocalDateTime time
    );

    List<WorkerSchedule> findByWorkerAndJobRequest(WorkerProfile worker, JobRequest jobRequest);
}

