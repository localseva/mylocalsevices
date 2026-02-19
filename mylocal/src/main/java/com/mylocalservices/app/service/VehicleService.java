//package com.mylocalservices.app.service;
//
//import com.mylocalservices.app.dto.response.VehicleResponse;
//import com.mylocalservices.app.mapper.VehicleMapper;
//import com.mylocalservices.app.repository.VehicleRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class VehicleService {
//
//    private final VehicleRepository vehicleRepository;
//    private final VehicleMapper mapper;
//
//    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper mapper) {
//        this.vehicleRepository = vehicleRepository;
//        this.mapper = mapper;
//    }
//
//    public List<VehicleResponse> getAllVehicles() {
//        return vehicleRepository.findAll()
//                .stream()
//                .map(mapper::toDto)
//                .toList();
//    }
//}
