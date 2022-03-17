package com.jobbed.api.company.domain;

import com.jobbed.api.company.domain.command.CreateCompanyCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CompanyFactory {

    public static CompanyAggregate create(CreateCompanyCommand command) {
        return CompanyAggregate.builder()
                .name(command.name())
                .build();
    }
}
