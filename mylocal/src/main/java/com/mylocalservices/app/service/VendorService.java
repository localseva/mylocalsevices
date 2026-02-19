//package com.mylocalservices.app.service;
//
//import com.mylocalservices.app.dto.request.VendorRequest;
//import com.mylocalservices.app.dto.response.VendorResponse;
//import com.mylocalservices.app.entity.Vendor;
//import com.mylocalservices.app.exception.ResourceNotFoundException;
//import com.mylocalservices.app.repository.VendorRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class VendorService {
//
//    private final VendorRepository vendorRepo;
//
//    public VendorService(VendorRepository vendorRepo) {
//        this.vendorRepo = vendorRepo;
//    }
//
//    // Register vendor
//    public VendorResponse registerVendor(VendorRequest request) {
//        Vendor vendor = Vendor.builder()
//                .vendorName(request.getVendorName())
//                .email(request.getEmail())
//                .phone(request.getPhone())
//                .approved(false)
//                .build();
//
//        vendorRepo.save(vendor);
//        return toDto(vendor);
//    }
//
//    // Approve vendor (Admin)
//    public VendorResponse approveVendor(Long vendorId) {
//        Vendor vendor = vendorRepo.findById(vendorId)
//                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
//
//        vendor.setApproved(true);
//        vendorRepo.save(vendor);
//
//        return toDto(vendor);
//    }
//
//    // List all vendors
//    public List<VendorResponse> getAllVendors() {
//        return vendorRepo.findAll()
//                .stream()
//                .map(this::toDto)
//                .toList();
//    }
//
//    private VendorResponse toDto(Vendor vendor) {
//        VendorResponse dto = new VendorResponse();
//        dto.setId(vendor.getId());
//        dto.setVendorName(vendor.getVendorName());
//        dto.setEmail(vendor.getEmail());
//        dto.setPhone(vendor.getPhone());
//        dto.setApproved(vendor.isApproved());
//        return dto;
//    }
//}
