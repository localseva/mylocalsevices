package com.mylocalservices.app.dto.request;

import lombok.Data;

@Data
public class VendorRequest {
    private String vendorName;
    private String email;
    private String phone;
}
