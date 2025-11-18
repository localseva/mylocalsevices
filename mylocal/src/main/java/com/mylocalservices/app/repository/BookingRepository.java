package com.mylocalservices.app.repository;

import com.mylocalservices.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByVendorId(String vendorId);
}
