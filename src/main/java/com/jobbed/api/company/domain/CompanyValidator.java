package com.jobbed.api.company.domain;

import com.jobbed.api.company.domain.command.CreateCompanyCommand;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import org.apache.logging.log4j.util.Strings;

class CompanyValidator {

    private final CompanyRepository repository;
    private ErrorStatus errorStatus;

    public CompanyValidator(CompanyRepository repository) {
        this.repository = repository;
    }

    public void validate(CreateCompanyCommand command) {
        errorStatus = null;
        validateName(command.name());
    }

    private void validateName(String name) {
        if (Strings.isBlank(name)) {
            errorStatus = ErrorStatus.COMPANY_NAME_NULL;
        } else if (repository.findByName(name).isPresent()) {
            errorStatus = ErrorStatus.COMPANY_NAME_TAKEN;
        }
        throwExceptionOnErrors();
    }

    private void throwExceptionOnErrors() {
        if (errorStatus == null) {
            return;
        }
        throw new ValidationException(errorStatus);
    }
}
