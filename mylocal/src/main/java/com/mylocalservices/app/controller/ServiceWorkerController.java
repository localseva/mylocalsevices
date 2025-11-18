package com.mylocalservices.app.controller;

import com.mylocalservices.app.dto.request.CreateBookingRequest;
import com.mylocalservices.app.dto.response.BookingResponse;
import com.mylocalservices.app.dto.response.WorkerResponse;
import com.mylocalservices.app.service.BookingService;
import com.mylocalservices.app.service.ServiceWorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class ServiceWorkerController {

    private final ServiceWorkerService workerService;
    private final BookingService bookingService;

    public ServiceWorkerController(ServiceWorkerService workerService,
                                   BookingService bookingService) {
        this.workerService = workerService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<WorkerResponse>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookWorker(
            @RequestBody CreateBookingRequest request,
            Principal principal) {

        // Extract userId from JWT or Principal
        Long userId = Long.parseLong(principal.getName());

        // Call the unified createBooking() method
        BookingResponse response = bookingService.createBooking(userId, request);
        return ResponseEntity.ok(response);
    }
}
