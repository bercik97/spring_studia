package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "workplace", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class WorkplaceEntity extends BaseEntity {

    private String name;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private String companyName;

    public WorkplaceAggregate toAggregate() {
        return WorkplaceAggregate.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .name(name)
                .address(address)
                .description(description)
                .latitude(latitude)
                .longitude(longitude)
                .companyName(companyName)
                .build();
    }
}
