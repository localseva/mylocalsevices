package com.mylocalservices.app.controller.service.provider.management.worker;

import com.mylocalservices.app.dto.service.provider.management.workers.WorkerSearchRequest;
import com.mylocalservices.app.dto.service.provider.management.workers.WorkerSearchResponse;
import com.mylocalservices.app.service.service.provider.management.WorkerSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
