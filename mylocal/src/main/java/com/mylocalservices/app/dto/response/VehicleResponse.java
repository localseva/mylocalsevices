package com.mylocalservices.app.dto.response;

import lombok.Data;

@Data
public class VehicleResponse {
    private String id;
    private String model;
    private int capacity;
    private double price;
}
