package com.mylocalservices.app.controller.service.provider.management.worker;

import com.mylocalservices.app.dto.service.provider.management.workers.WorkerProfileRequest;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerProfileResponse;
import com.mylocalservices.app.service.service.provider.management.WorkerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worker/profile")
@RequiredArgsConstructor
public class WorkerProfileController {

    private final WorkerProfileService service;

    // 👷 Create Profile
    @PostMapping
    public WorkerProfileResponse createProfile(
            @RequestBody WorkerProfileRequest request,
            Authentication auth) {

        String email = auth.getName(); // from JWT
        return service.createProfile(email, request);
    }

    // 👤 Get My Profile
    @GetMapping("/me")
    public WorkerProfileResponse getMyProfile(Authentication auth) {

        String email = auth.getName();
        return service.getMyProfile(email);
    }
}
