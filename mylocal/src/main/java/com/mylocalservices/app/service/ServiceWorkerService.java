//package com.mylocalservices.app.service;
//
//import com.mylocalservices.app.dto.response.WorkerResponse;
//import com.mylocalservices.app.entity.ServiceWorker;
//import com.mylocalservices.app.repository.ServiceWorkerRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ServiceWorkerService {
//
//    private final ServiceWorkerRepository workerRepo;
//
//    public ServiceWorkerService(ServiceWorkerRepository workerRepo) {
//        this.workerRepo = workerRepo;
//    }
//
//    public List<WorkerResponse> getAllWorkers() {
//        return workerRepo.findAll()
//                .stream()
//                .map(this::mapToDto)
//                .toList();
//    }
//
//    private WorkerResponse mapToDto(ServiceWorker worker) {
//        WorkerResponse dto = new WorkerResponse();
//        dto.setId(worker.getId());
//        dto.setName(worker.getName());
//        dto.setWorkType(worker.getWorkType());
//        dto.setPrice(worker.getPrice());
//        dto.setStatus(worker.getStatus());
//        return dto;
//    }
//}
