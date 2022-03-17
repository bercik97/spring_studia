package com.jobbed.api.workplace.domain.dto;

import com.jobbed.api.workplace.domain.WorkplaceAggregate;
import com.jobbed.api.workplace.domain.vo.WorkplaceLocation;

import java.time.ZonedDateTime;

public record WorkplaceDto(long id,
                           ZonedDateTime createdDate,
                           String name,
                           String address,
                           String description,
                           WorkplaceLocation location,
                           String companyName) {

    public static WorkplaceDto from(WorkplaceAggregate workplaceAggregate) {
        return new WorkplaceDto(
                workplaceAggregate.getId(),
                workplaceAggregate.getCreatedDate(),
                workplaceAggregate.getName(),
                workplaceAggregate.getAddress(),
                workplaceAggregate.getDescription(),
                new WorkplaceLocation(workplaceAggregate.getLatitude(), workplaceAggregate.getLongitude()),
                workplaceAggregate.getCompanyName());
    }
}
