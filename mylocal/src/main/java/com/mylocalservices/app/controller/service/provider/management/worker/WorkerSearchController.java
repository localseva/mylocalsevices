package com.mylocalservices.app.controller.service.provider.management.worker;

import com.mylocalservices.app.dto.service.provider.management.workers.WorkerReviewsResponse;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerSearchRequest;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerSearchResponse;
import com.mylocalservices.app.service.service.provider.management.WorkerSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerSearchController {

    @Autowired
    private WorkerSearchService service;

    @PostMapping("/search")
    public ResponseEntity<Page<WorkerSearchResponse>> searchWorkers(
            @RequestBody WorkerSearchRequest request
    ) {
        return ResponseEntity.ok(service.search(request));
    }

    @GetMapping
    public ResponseEntity<Page<WorkerSearchResponse>> getAllWorkers(
    ) {
        return ResponseEntity.ok(service.getAllWorkers());
    }

    @GetMapping("/reviews/{workerId}")
    public ResponseEntity<List<WorkerReviewsResponse>> getReviewListByWorkerId(@PathVariable Long workerId) {
        return ResponseEntity.ok(service.getReviewsByWorkerId(workerId));
    }
}
