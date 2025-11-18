package com.mylocalservices.app.dto.request;

import lombok.Data;

@Data
public class CreateBookingRequest {
    private Long vehicleId;
    private Long serviceId;
    private String date;
    private String timeSlot;
}
