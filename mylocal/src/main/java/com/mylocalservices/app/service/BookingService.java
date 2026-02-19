//package com.mylocalservices.app.service;
//
//import com.mylocalservices.app.dto.request.CreateBookingRequest;
//import com.mylocalservices.app.dto.response.BookingResponse;
//import com.mylocalservices.app.entity.ServiceWorker;
//import com.mylocalservices.app.entity.Vehicle;
//import com.mylocalservices.app.entity.Booking;
//import com.mylocalservices.app.exception.ResourceNotFoundException;
//import com.mylocalservices.app.repository.ServiceWorkerRepository;
//import com.mylocalservices.app.repository.VehicleRepository;
//import com.mylocalservices.app.repository.BookingRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BookingService {
//
//    private final VehicleRepository vehicleRepository;
//    private final ServiceWorkerRepository workerRepository;
//    private final BookingRepository bookingRepository;
//
//    public BookingService(VehicleRepository vehicleRepository,
//                          ServiceWorkerRepository workerRepository,
//                          BookingRepository bookingRepository) {
//        this.vehicleRepository = vehicleRepository;
//        this.workerRepository = workerRepository;
//        this.bookingRepository = bookingRepository;
//    }
//
//    // ------------------------ CREATE BOOKING ------------------------
//    public BookingResponse createBooking(Long userId, CreateBookingRequest req) {
//
//        Booking booking = new Booking();
//        booking.setUserId(userId);
//        booking.setDate(req.getDate());
//        booking.setTimeSlot(req.getTimeSlot());
//
//        if (req.getVehicleId() != null) {
//            Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
//
//            booking.setVehicleId(vehicle.getId());
//            booking.setItemName(vehicle.getModel());
//        }
//
//        if (req.getServiceId() != null) {
//            ServiceWorker worker = workerRepository.findById(req.getServiceId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Service/Worker not found"));
//
//            booking.setServiceId(worker.getId());
//            booking.setItemName(worker.getName());
//        }
//
//        booking.setStatus("BOOKED");
//
//        bookingRepository.save(booking);
//
//        return BookingResponse.builder()
//                .id(booking.getId())
//                .userId(booking.getUserId())
//                .vehicleId(booking.getVehicleId())
//                .serviceId(booking.getServiceId())
//                .status(booking.getStatus())
//                .date(booking.getDate())
//                .timeSlot(booking.getTimeSlot())
//                .build();
//    }
//
//    // ------------------------ GET USER BOOKINGS ------------------------
//    public List<BookingResponse> getBookingsByUser(Long userId) {
//        return bookingRepository.findByUserId(userId)
//                .stream()
//                .map(b -> BookingResponse.builder()
//                        .id(b.getId())
//                        .userId(b.getUserId())
//                        .vehicleId(b.getVehicleId())
//                        .serviceId(b.getServiceId())
//                        .status(b.getStatus())
//                        .date(b.getDate())
//                        .timeSlot(b.getTimeSlot())
//                        .build())
//                .collect(Collectors.toList());
//    }
//}