package com.mylocalservices.app.service;

import com.mylocalservices.app.dto.response.ServiceItemResponse;
import com.mylocalservices.app.entity.ServiceItem;
import com.mylocalservices.app.repository.ServiceItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemService {

    private final ServiceItemRepository repo;

    public ServiceItemService(ServiceItemRepository repo) {
        this.repo = repo;
    }

    public List<ServiceItemResponse> getAllServices() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ServiceItemResponse toDto(ServiceItem item) {
        return ServiceItemResponse.builder()
                .id(item.getId())
                .vendorId(item.getVendor().getId())
                .serviceType(item.getServiceType())
                .price(item.getPrice())
                .build();
    }
}
