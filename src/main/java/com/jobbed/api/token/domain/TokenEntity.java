package com.jobbed.api.token.domain;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.shared.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "token", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class TokenEntity extends BaseEntity {

    private String code;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    private String companyName;

    public TokenAggregate toAggregate() {
        return TokenAggregate.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .code(code)
                .roleType(roleType)
                .companyName(companyName)
                .build();
    }
}
