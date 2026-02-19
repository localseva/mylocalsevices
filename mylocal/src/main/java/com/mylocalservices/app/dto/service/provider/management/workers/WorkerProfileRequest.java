package com.mylocalservices.app.dto.service.provider.management.workers;

import com.mylocalservices.app.enums.auth.worker.ServiceType;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WorkerProfileRequest {

    private ServiceType serviceType;
    private Integer experienceYears;
    private String description;

    private String city;
    private String area;
    private String pincode;

    private BigDecimal baseFare;
    private Boolean available;
}
