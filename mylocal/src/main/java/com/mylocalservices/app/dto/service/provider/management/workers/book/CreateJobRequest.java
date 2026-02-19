package com.mylocalservices.app.dto.service.provider.management.workers.book;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateJobRequest {

    private Long workerId;

    @JsonAlias("notes")
    private String description;

    private String address;
    private String city;
    private String pincode;

    // Job start and end time
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime scheduledAt;
}
