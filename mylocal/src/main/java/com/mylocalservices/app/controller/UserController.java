package com.mylocalservices.app.controller;

import com.mylocalservices.app.dto.request.CreateBookingRequest;
import com.mylocalservices.app.dto.response.BookingResponse;
import com.mylocalservices.app.dto.response.VehicleResponse;
import com.mylocalservices.app.dto.response.ServiceItemResponse;
import com.mylocalservices.app.service.ServiceItemService;
import com.mylocalservices.app.service.VehicleService;
import com.mylocalservices.app.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final VehicleService vehicleService;
    private final ServiceItemService serviceItemService;
    private final BookingService bookingService;

    public UserController(VehicleService vehicleService,
                          ServiceItemService serviceItemService,
                          BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.serviceItemService = serviceItemService;
        this.bookingService = bookingService;
    }

    // -------------------------------
    // USER APIs
    // -------------------------------

    // 1️⃣ Get All Vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleResponse>> getVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    // 2️⃣ Get All Services
    @GetMapping("/services")
    public ResponseEntity<List<ServiceItemResponse>> getServices() {
        return ResponseEntity.ok(serviceItemService.getAllServices());
    }

    // 3️⃣ Create Booking
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody CreateBookingRequest request,
            Principal principal) {

        Long userId = extractUserId(principal);

        BookingResponse response = bookingService.createBooking(userId, request);
        return ResponseEntity.ok(response);
    }

    // 4️⃣ Get My Bookings
    @GetMapping("/bookings/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            Principal principal) {

        Long userId = extractUserId(principal);
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    // -------------------------------
    // Helper: Extract User ID From JWT
    // -------------------------------
    private Long extractUserId(Principal principal) {
        // ⚠️ Replace this with your JWT implementation
        // Example: principal.getName() = "userId"
        return Long.parseLong(principal.getName());
    }
}
