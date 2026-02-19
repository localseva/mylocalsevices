package com.mylocalservices.app.service.service.provider.management;

import com.mylocalservices.app.dto.service.provider.management.workers.WorkerProfileRequest;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerProfileResponse;
import com.mylocalservices.app.entity.auth.User;
import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.enums.auth.worker.Role;
import com.mylocalservices.app.repository.auth.UserRepository;
import com.mylocalservices.app.repository.service.provider.management.WorkerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerProfileService {

    private final WorkerProfileRepository profileRepo;
    private final UserRepository userRepo;

    // 👷 Create Profile
    public WorkerProfileResponse createProfile(
            String email,
            WorkerProfileRequest request) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() == Role.CUSTOMER){
            throw new RuntimeException("Only workers can create profiles");
        }

        WorkerProfile profile = WorkerProfile.builder()
                .user(user)
                .serviceType(request.getServiceType())
                .experienceYears(request.getExperienceYears())
                .description(request.getDescription())
                .city(request.getCity().toLowerCase())
                .area(request.getArea().toLowerCase())
                .pincode(request.getPincode())
                .baseFare(request.getBaseFare())
                .available(request.getAvailable())
                .build();

        profileRepo.save(profile);

        return mapToResponse(profile);
    }

    // 👤 Get Own Profile
    public WorkerProfileResponse getMyProfile(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkerProfile profile = profileRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return mapToResponse(profile);
    }

    private WorkerProfileResponse mapToResponse(WorkerProfile p) {
        return WorkerProfileResponse.builder()
                .id(p.getId())
                .name(p.getUser().getName())
                .phone(p.getUser().getPhone())
                .serviceType(p.getServiceType())
                .experienceYears(p.getExperienceYears())
                .description(p.getDescription())
                .city(p.getCity())
                .area(p.getArea())
                .pincode(p.getPincode())
                .baseFare(p.getBaseFare())
                .available(p.getAvailable())
                .build();
    }
}
