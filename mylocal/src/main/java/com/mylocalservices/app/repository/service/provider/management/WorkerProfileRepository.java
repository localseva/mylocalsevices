package com.mylocalservices.app.repository.service.provider.management;

import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.enums.auth.worker.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkerProfileRepository
        extends JpaRepository<WorkerProfile, Long> {

    Optional<WorkerProfile> findByUser(User user);
}
