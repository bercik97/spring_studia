package com.jobbed.api.company.domain;

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
public class CompanyAggregate {

    private long id;
    private ZonedDateTime createdDate;
    private String name;

    public CompanyEntity toEntity() {
        CompanyEntity entity = CompanyEntity.builder()
                .name(name)
                .build();
        entity.setId(id);
        return entity;
    }
}
