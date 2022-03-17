package com.jobbed.api.company.domain;

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
@Table(name = "company", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class CompanyEntity extends BaseEntity {

    private String name;

    public CompanyAggregate toAggregate() {
        return CompanyAggregate.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .name(name)
                .build();
    }
}
