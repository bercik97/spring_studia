package com.jobbed.api.workplace.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkplaceAggregate {

    private long id;
    private ZonedDateTime createdDate;
    private String name;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private String companyName;

    public WorkplaceEntity toEntity() {
        WorkplaceEntity entity = WorkplaceEntity.builder()
                .name(name)
                .address(address)
                .description(description)
                .latitude(latitude)
                .longitude(longitude)
                .companyName(companyName)
                .build();
        entity.setId(id);
        return entity;
    }
}
