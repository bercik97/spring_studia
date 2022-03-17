package com.jobbed.api.company.domain;

import com.jobbed.api.company.domain.command.CreateCompanyCommand;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CompanyService {

    private final CompanyRepository repository;
    private final CompanyValidator validator;

    public CompanyAggregate create(CreateCompanyCommand command) {
        validator.validate(command);
        CompanyAggregate company = CompanyFactory.create(command);
        repository.save(company);
        return company;
    }

    public CompanyAggregate findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new NotFoundException(ErrorStatus.COMPANY_NOT_FOUND));
    }
}
