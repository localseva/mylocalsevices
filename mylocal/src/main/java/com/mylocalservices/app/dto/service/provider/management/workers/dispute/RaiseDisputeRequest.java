package com.mylocalservices.app.dto.service.provider.management.workers.dispute;

import lombok.Data;

@Data
public class RaiseDisputeRequest {
    private Long jobId;
    private String reason;
    private String description;
}
