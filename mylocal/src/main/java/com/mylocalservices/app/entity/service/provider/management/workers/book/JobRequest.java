package com.mylocalservices.app.entity.service.provider.management.workers.book;

import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.enums.auth.worker.CancelledBy;
import com.mylocalservices.app.enums.worker.book.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👤 Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // 🧑‍🔧 Worker
    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerProfile worker;

    // 📌 Job Details
    private String serviceType;

    @Column(length = 1000)
    private String description;

    // 📍 Service Location
    private String address;
    private String city;
    private String pincode;

    // 📅 Schedule
    private LocalDateTime scheduledAt;

    // 📊 Status
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    // 🕒 Audit
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean completed;
    private Integer rating;        // 1-5
    private String feedback;

    private boolean cancelled;

    @Enumerated(EnumType.STRING)
    private CancelledBy cancelledBy;   // USER / WORKER

    private String cancellationReason;

    private LocalDateTime cancelledAt;

}
