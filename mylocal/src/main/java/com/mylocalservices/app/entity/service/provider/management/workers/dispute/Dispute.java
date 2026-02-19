package com.mylocalservices.app.entity.service.provider.management.workers.dispute;

import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.enums.auth.worker.DisputeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "disputes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Related job
    @ManyToOne
    private JobRequest job;

    // 👤 Who raised it
    @ManyToOne
    private User raisedBy;

    @Column(length = 2000)
    private String reason;

    @Enumerated(EnumType.STRING)
    private DisputeStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
