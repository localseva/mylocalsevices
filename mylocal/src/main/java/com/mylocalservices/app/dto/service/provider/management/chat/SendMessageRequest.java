package com.mylocalservices.app.dto.service.provider.management.chat;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long jobId;
    private String message;
}
