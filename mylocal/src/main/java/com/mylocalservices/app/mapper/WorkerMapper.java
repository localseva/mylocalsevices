package com.mylocalservices.app.mapper;

import com.mylocalservices.app.dto.response.WorkerResponse;
import com.mylocalservices.app.entity.ServiceWorker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    WorkerResponse toDto(ServiceWorker worker);
}
