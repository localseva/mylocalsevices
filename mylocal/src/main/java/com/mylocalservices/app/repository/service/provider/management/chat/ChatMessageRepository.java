package com.mylocalservices.app.repository.service.provider.management.chat;

import com.mylocalservices.app.entity.service.provider.management.workers.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByJob_IdOrderByCreatedAtAsc(Long jobId);

}
