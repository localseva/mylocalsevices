package com.mylocalservices.app.service.service.provider.management.dispute;

import com.mylocalservices.app.dto.service.provider.management.workers.dispute.RaiseDisputeRequest;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.book.JobRequest;
import com.mylocalservices.app.entity.service.provider.management.workers.dispute.Dispute;
import com.mylocalservices.app.enums.auth.worker.DisputeStatus;
import com.mylocalservices.app.enums.auth.worker.Role;
import com.mylocalservices.app.repository.auth.UserRepository;
import com.mylocalservices.app.repository.service.provider.management.book.JobRequestRepository;
import com.mylocalservices.app.repository.service.provider.management.dispute.DisputeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisputeService {

    private final DisputeRepository disputeRepository;
    private final JobRequestRepository jobRepository;
    private final UserRepository userRepository;

    /**
     * Raise a dispute for a job
     */
    public Dispute raiseDispute(RaiseDisputeRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobRequest job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Dispute dispute = Dispute.builder()
                .job(job)
                .raisedBy(user)
                .reason(request.getReason())
                .status(DisputeStatus.OPEN)
                .build();

        return disputeRepository.save(dispute);
    }

    /**
     * Get disputes for a job
     */
    @Transactional(readOnly = true)
    public List<Dispute> getDisputesByJob(Long jobId) {

        if (!jobRepository.existsById(jobId)) {
            throw new RuntimeException("Job not found");
        }

        return disputeRepository.findByJob_Id(jobId);
    }

    /**
     * Get disputes raised by logged-in email
     */
    @Transactional(readOnly = true)
    public List<Dispute> getDisputesByUser(String email) {

        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return disputeRepository.findByRaisedBy_Email(email);
    }

    public Dispute closeDispute(Long disputeId, String email) {

        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔐 Authorization check
        boolean isOwner = dispute.getRaisedBy().getId().equals(user.getId());
        boolean isWorker = dispute.getRaisedBy().getRole() == Role.WORKER;
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isWorker && !isAdmin) {
            throw new RuntimeException("You are not allowed to close this dispute");
        }

        // ❌ Prevent closing already resolved disputes
        if (dispute.getStatus() == DisputeStatus.RESOLVED) {
            throw new RuntimeException("Dispute already closed/resolved");
        }

        dispute.setStatus(DisputeStatus.RESOLVED);

        return disputeRepository.save(dispute);
    }
}
