package com.jobbed.api.token.domain.dto;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.token.domain.TokenAggregate;

public record TokenDto(String code, RoleType roleType, String companyName) {

    public static TokenDto of(TokenAggregate tokenAggregate) {
        return new TokenDto(tokenAggregate.getCode(), tokenAggregate.getRoleType(), tokenAggregate.getCompanyName());
    }
}
