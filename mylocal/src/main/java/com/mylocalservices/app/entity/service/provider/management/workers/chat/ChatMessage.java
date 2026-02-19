package com.mylocalservices.app.entity.service.provider.management.workers.chat;

import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Link to Job
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private JobRequest job;

    // 👤 Sender
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // 💬 Message content
    @Column(length = 2000)
    private String message;

    // ✔️ Read status
    private boolean read;

    // 🕒 Timestamp
    @CreationTimestamp
    private LocalDateTime createdAt;
}
