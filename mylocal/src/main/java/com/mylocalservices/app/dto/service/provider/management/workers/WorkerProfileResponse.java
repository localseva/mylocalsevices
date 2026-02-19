package com.mylocalservices.app.dto.service.provider.management.workers;

import com.mylocalservices.app.enums.auth.worker.ServiceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WorkerProfileResponse {

    private Long id;

    private String name;
    private String phone;

    private ServiceType serviceType;
    private Integer experienceYears;
    private String description;

    private String city;
    private String area;
    private String pincode;

    private BigDecimal baseFare;
    private Boolean available;
}
