//package com.mylocalservices.app.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "bookings")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Booking {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long userId;
//
//    // Either vehicleId or serviceId will be set depending on booking type
//    private Long vehicleId;
//    private Long serviceId;
//
//    private String itemName; // Name of vehicle or service
//
//    private String status; // BOOKED, ACCEPTED, REJECTED, etc.
//
//    private String date;
//    private String timeSlot;
//}
