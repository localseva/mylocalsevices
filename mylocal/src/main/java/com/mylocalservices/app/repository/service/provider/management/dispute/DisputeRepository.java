package com.mylocalservices.app.repository.service.provider.management.dispute;


import com.mylocalservices.app.dto.service.provider.management.workers.dispute.RaiseDisputeRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.dispute.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputeRepository
        extends JpaRepository<Dispute, Long> {
    List<Dispute> findByJob_Id(Long jobId);

    List<Dispute> findByRaisedBy_Email(String email);
}

