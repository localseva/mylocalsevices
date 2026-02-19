//package com.mylocalservices.app.controller.barat;
//
//import com.mylocalservices.app.dto.request.VendorRequest;
//import com.mylocalservices.app.dto.response.VendorResponse;
//import com.mylocalservices.app.service.VendorService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/vendor")
//public class VendorController {
//
//    private final VendorService vendorService;
//
//    public VendorController(VendorService vendorService) {
//        this.vendorService = vendorService;
//    }
//
//    @PostMapping("/register")
//    public VendorResponse registerVendor(@RequestBody VendorRequest request) {
//        return vendorService.registerVendor(request);
//    }
//
//    @GetMapping
//    public List<VendorResponse> getAllVendors() {
//        return vendorService.getAllVendors();
//    }
//}
