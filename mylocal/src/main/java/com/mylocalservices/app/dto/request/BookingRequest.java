package com.mylocalservices.app.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookingRequest {

    @NotNull(message = "Item ID cannot be null")
    private Long itemId;

    @NotNull(message = "Date is required")
    @Size(min = 5, message = "Date is invalid")
    private String date;

    @NotNull(message = "Customer name required")
    private String customerName;
}
