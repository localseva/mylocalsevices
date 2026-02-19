//package com.mylocalservices.app.controller.barat;
//
//import com.mylocalservices.app.dto.response.ServiceItemResponse;
//import com.mylocalservices.app.service.ServiceItemService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/services")
//public class ServiceItemController {
//
//    private final ServiceItemService service;
//
//    public ServiceItemController(ServiceItemService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public List<ServiceItemResponse> getAll() {
//        return service.getAllServices();
//    }
//}
