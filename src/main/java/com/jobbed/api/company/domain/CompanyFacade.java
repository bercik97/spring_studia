package com.jobbed.api.company.domain;

import com.jobbed.api.company.domain.command.CreateCompanyCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyFacade {

    private final CompanyService service;

    public CompanyAggregate create(CreateCompanyCommand command) {
        return service.create(command);
    }

    public CompanyAggregate findByName(String name) {
        return service.findByName(name);
    }
}
