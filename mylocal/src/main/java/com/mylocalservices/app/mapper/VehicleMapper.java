package com.mylocalservices.app.mapper;

import com.mylocalservices.app.dto.response.VehicleResponse;
import com.mylocalservices.app.entity.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleResponse toDto(Vehicle vehicle);
}
