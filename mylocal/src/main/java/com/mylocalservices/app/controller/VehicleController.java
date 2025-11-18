package com.mylocalservices.app.controller;

import com.mylocalservices.app.dto.request.CreateBookingRequest;
import com.mylocalservices.app.dto.response.BookingResponse;
import com.mylocalservices.app.dto.response.VehicleResponse;
import com.mylocalservices.app.service.BookingService;
import com.mylocalservices.app.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final BookingService bookingService;

    public VehicleController(VehicleService vehicleService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookVehicle(
            @Valid @RequestBody CreateBookingRequest request,
            Principal principal) {

        // Extract userId from JWT or principal
        Long userId = Long.parseLong(principal.getName());

        // Call the new createBooking() method
        BookingResponse response = bookingService.createBooking(userId, request);
        return ResponseEntity.ok(response);
    }
}
