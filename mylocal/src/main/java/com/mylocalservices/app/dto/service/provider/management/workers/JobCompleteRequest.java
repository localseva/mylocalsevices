package com.mylocalservices.app.dto.service.provider.management.workers;

import lombok.Data;

@Data
public class JobCompleteRequest {
    private Long jobId;
    private Integer rating;  // 1-5
    private String feedback;
}
