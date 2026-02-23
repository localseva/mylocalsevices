package com.mylocalservices.app.dto.service.provider.management;

import lombok.Data;

@Data
public class JobCompleteRequest {
    private Long jobId;
    private Integer rating;  // 1-5
    private String feedback;
}
