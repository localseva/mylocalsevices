package com.mylocalservices.app.dto.service.provider.management.workers;

import lombok.Data;

import java.util.List;

@Data
public class WorkerReviewsResponse {
    private String customerName;
    private String Comment;
    private Integer rating;
}
