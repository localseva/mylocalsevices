package com.mylocalservices.app.repository;

import com.mylocalservices.app.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
