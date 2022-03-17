package com.jobbed.api.token.domain;

import com.jobbed.api.role.RoleType;
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
public class TokenAggregate {

    private long id;
    private ZonedDateTime createdDate;
    private String code;
    private RoleType roleType;
    private String companyName;

    public TokenEntity toEntity() {
        TokenEntity entity = TokenEntity.builder()
                .code(code)
                .roleType(roleType)
                .companyName(companyName)
                .build();
        entity.setId(id);
        return entity;
    }
}
