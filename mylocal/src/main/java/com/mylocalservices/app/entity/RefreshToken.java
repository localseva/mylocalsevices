//package com.mylocalservices.app.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.Instant;
//
//@Entity
//@Table(name = "refresh_tokens")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class RefreshToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String token;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    private Instant expiryDate;
//    @Builder.Default
//    private boolean revoked = false;
//}
