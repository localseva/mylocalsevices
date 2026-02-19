package com.mylocalservices.app.dto.service.provider.management.workers.book;

import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.enums.auth.worker.CancelledBy;
import com.mylocalservices.app.enums.worker.book.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO {
    private Long id;
    
    // Customer info
    private Long customerId;
    private String customerName;
    private String customerEmail;
    
    // Worker info
    private Long workerId;
    private String workerName;
    
    // Job details
    private String serviceType;
    private String description;
    
    // Location
    private String address;
    private String city;
    private String pincode;
    
    // Schedule
    private LocalDateTime scheduledAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    // Status
    private JobStatus status;
    
    // Feedback
    private boolean completed;
    private Integer rating;
    private String feedback;
    
    // Cancellation
    private boolean cancelled;
    private CancelledBy cancelledBy;
    private String cancellationReason;
    private LocalDateTime cancelledAt;
    
    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static JobRequestDTO fromEntity(JobRequest job) {
        return JobRequestDTO.builder()
                .id(job.getId())
                .customerId(job.getCustomer().getId())
                .customerName(job.getCustomer().getName())
                .customerEmail(job.getCustomer().getEmail())
                .workerId(job.getWorker().getId())
                .workerName(job.getWorker().getUser().getName())
                .serviceType(job.getServiceType())
                .description(job.getDescription())
                .address(job.getAddress())
                .city(job.getCity())
                .pincode(job.getPincode())
                .scheduledAt(job.getScheduledAt())
                .startTime(job.getStartTime())
                .endTime(job.getEndTime())
                .status(job.getStatus())
                .completed(job.isCompleted())
                .rating(job.getRating())
                .feedback(job.getFeedback())
                .cancelled(job.isCancelled())
                .cancelledBy(job.getCancelledBy())
                .cancellationReason(job.getCancellationReason())
                .cancelledAt(job.getCancelledAt())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
}
