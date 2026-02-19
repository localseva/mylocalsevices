package com.mylocalservices.app.entity.service.provider.management.workers.book;

import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.enums.auth.worker.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "worker_schedules",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_worker_time",
                columnNames = {"worker_id", "startTime", "endTime"}
        ),
        indexes = {
                @Index(name = "idx_worker_time", columnList = "worker_id, startTime, endTime"),
                @Index(name = "idx_status", columnList = "status")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Worker
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerProfile worker;

    // 🕒 Time Slot
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    // 📊 Slot Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    // 🔗 Booking reference (optional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_request_id")
    private JobRequest jobRequest;

    // 📝 Optional notes
    private String notes;

    // 🕒 Audit
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ⭐ Helper methods
    public boolean isAvailable() {
        return status == ScheduleStatus.AVAILABLE;
    }

    public boolean isBooked() {
        return status == ScheduleStatus.BOOKED;
    }
}

