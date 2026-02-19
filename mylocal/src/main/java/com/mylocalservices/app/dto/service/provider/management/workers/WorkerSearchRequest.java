package com.mylocalservices.app.dto.service.provider.management.workers;

import com.mylocalservices.app.enums.auth.worker.ServiceType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WorkerSearchRequest {

    private ServiceType serviceType;

    private String city;
    private String area;
    private String pincode;

    // Advanced filters
    private Integer minExperienceYears;
    private Integer maxExperienceYears;

    private BigDecimal minBaseFare;
    private BigDecimal maxBaseFare;

    // Availability slots
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;

    private LocalDateTime specificDate;

    // Pagination
    private int page = 0;
    private int size = 10;
}
