package com.jobbed.api.token.domain;

import com.jobbed.api.token.domain.command.CreateTokenCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TokenFactory {

    public static TokenAggregate create(String code, CreateTokenCommand command) {
        return TokenAggregate.builder()
                .code(code)
                .roleType(command.roleType())
                .companyName(command.companyName())
                .build();
    }
}
