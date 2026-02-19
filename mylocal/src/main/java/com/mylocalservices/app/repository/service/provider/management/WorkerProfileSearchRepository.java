package com.mylocalservices.app.repository.service.provider.management;

import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.enums.auth.worker.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface WorkerProfileSearchRepository
        extends JpaRepository<WorkerProfile, Long> {

    // Basic search without availability checks
    @Query("""
    SELECT w FROM WorkerProfile w
    WHERE w.available = true
      AND (:serviceType IS NULL OR w.serviceType = :serviceType)
      AND (:city IS NULL OR w.city = :city)
      AND (:area IS NULL OR w.area = :area)
      AND (:pincode IS NULL OR w.pincode = :pincode)
      AND (:minExp IS NULL OR w.experienceYears >= :minExp)
      AND (:maxExp IS NULL OR w.experienceYears <= :maxExp)
      AND (:minFare IS NULL OR w.baseFare >= :minFare)
      AND (:maxFare IS NULL OR w.baseFare <= :maxFare)
    """)
    Page<WorkerProfile> searchWorkers(
            @Param("serviceType") ServiceType serviceType,
            @Param("city") String city,
            @Param("area") String area,
            @Param("pincode") String pincode,
            @Param("minExp") Integer minExp,
            @Param("maxExp") Integer maxExp,
            @Param("minFare") BigDecimal minFare,
            @Param("maxFare") BigDecimal maxFare,
            Pageable pageable
    );
}
