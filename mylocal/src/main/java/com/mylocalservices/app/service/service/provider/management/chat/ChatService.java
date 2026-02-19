package com.mylocalservices.app.service.service.provider.management.chat;

import com.mylocalservices.app.dto.service.provider.management.workers.chat.SendMessageRequest;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.chat.ChatMessage;
import com.mylocalservices.app.repository.auth.UserRepository;
import com.mylocalservices.app.repository.service.provider.management.book.JobRequestRepository;
import com.mylocalservices.app.repository.service.provider.management.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatRepository;
    private final JobRequestRepository jobRepository;
    private final UserRepository userRepository;

    public ChatMessage sendMessage(SendMessageRequest request, String email) {

        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobRequest job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // 🔒 Security: Only job participants can chat
        if (!job.getCustomer().getId().equals(sender.getId()) &&
            !job.getWorker().getUser().getId().equals(sender.getId())) {
            throw new RuntimeException("Not authorized to chat on this job");
        }

        ChatMessage msg = ChatMessage.builder()
                .job(job)
                .sender(sender)
                .message(request.getMessage())
                .read(false)
                .build();

        return chatRepository.save(msg);
    }

    public List<ChatMessage> getChat(Long jobId) {
        return chatRepository.findByJob_IdOrderByCreatedAtAsc(jobId);
    }
}
