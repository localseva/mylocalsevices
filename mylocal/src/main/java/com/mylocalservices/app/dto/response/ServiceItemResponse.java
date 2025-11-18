package com.mylocalservices.app.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceItemResponse {
    private Long id;
    private Long vendorId;
    private String serviceType;
    private double price;
}
