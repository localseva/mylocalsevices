package com.mylocalservices.app.controller.service.provider.management.chat;

import com.mylocalservices.app.dto.service.provider.management.workers.chat.SendMessageRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.chat.ChatMessage;
import com.mylocalservices.app.service.service.provider.management.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/send")
    public ChatMessage send(
            @RequestBody SendMessageRequest request,
            Authentication auth) {

        return chatService.sendMessage(request, auth.getName());
    }

    @GetMapping("/{jobId}")
    public List<ChatMessage> getChat(@PathVariable Long jobId) {
        return chatService.getChat(jobId);
    }
}
