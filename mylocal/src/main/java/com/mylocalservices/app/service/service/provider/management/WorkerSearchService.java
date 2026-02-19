package com.mylocalservices.app.service.service.provider.management;

import com.mylocalservices.app.dto.service.provider.management.workers.WorkerSearchRequest;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerSearchResponse;
import com.mylocalservices.app.entity.service.provider.management.workers.WorkerProfile;
import com.mylocalservices.app.enums.auth.worker.ScheduleStatus;
import com.mylocalservices.app.repository.service.provider.management.WorkerProfileSearchRepository;
import com.mylocalservices.app.repository.service.provider.management.book.WorkerScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WorkerSearchService {

    private final WorkerProfileSearchRepository profileRepository;
    private final WorkerScheduleRepository scheduleRepository;

    public Page<WorkerSearchResponse> search(WorkerSearchRequest request) {

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("baseFare").ascending()
        );

        // Convert city and area to lowercase for case-insensitive comparison
        String city = request.getCity();
        String area = request.getArea();
        
        if (city != null) {
            city = city.toLowerCase();
        }
        
        if (area != null) {
            area = area.toLowerCase();
        }

        // Get basic worker profiles matching the criteria
        Page<WorkerProfile> profiles = profileRepository.searchWorkers(
                request.getServiceType(),
                city,
                area,
                request.getPincode(),
                request.getMinExperienceYears(),
                request.getMaxExperienceYears(),
                request.getMinBaseFare(),
                request.getMaxBaseFare(),
                pageable
        );

        // Filter by availability time range if specified
        if (request.getAvailableFrom() != null && request.getAvailableTo() != null) {
            List<WorkerProfile> filtered = profiles.getContent().stream()
                    .filter(profile ->
                            scheduleRepository
                                    .existsByWorker_IdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndStatus(
                                            profile.getId(),
                                            request.getAvailableTo(),
                                            request.getAvailableFrom(),
                                            ScheduleStatus.AVAILABLE
                                    )
                    )
                    .collect(Collectors.toList());

                profiles = new PageImpl<>(filtered, pageable, filtered.size());
        }

        // Filter by specific date if specified
        if (request.getSpecificDate() != null) {
            List<WorkerProfile> filtered = profiles.getContent().stream()
                    .filter(profile ->
                            scheduleRepository
                                    .existsByWorker_IdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndStatus(
                                            profile.getId(),
                                            request.getSpecificDate(),
                                            request.getSpecificDate(),
                                            ScheduleStatus.AVAILABLE
                                    )
                    )
                    .collect(Collectors.toList());

                profiles = new PageImpl<>(filtered, pageable, filtered.size());
        }

        return profiles.map(this::toResponse);
    }

    private WorkerSearchResponse toResponse(WorkerProfile p) {
        return WorkerSearchResponse.builder()
                .id(p.getId())
                .serviceType(p.getServiceType())
                .experienceYears(p.getExperienceYears())
                .description(p.getDescription())
                .city(p.getCity())
                .area(p.getArea())
                .pincode(p.getPincode())
                .baseFare(p.getBaseFare())
                .available(p.getAvailable())
                .phone(p.getUser().getPhone())
                .build();
    }
}