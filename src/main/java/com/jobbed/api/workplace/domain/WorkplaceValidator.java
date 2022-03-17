package com.jobbed.api.workplace.domain;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import com.jobbed.api.workplace.domain.command.CreateWorkplaceCommand;
import com.jobbed.api.workplace.domain.command.UpdateWorkplaceCommand;
import com.jobbed.api.workplace.domain.vo.WorkplaceLocation;
import org.apache.logging.log4j.util.Strings;

class WorkplaceValidator {

    private final WorkplaceRepository repository;
    private ErrorStatus errorStatus;

    public WorkplaceValidator(WorkplaceRepository repository) {
        this.repository = repository;
    }

    public void validate(CreateWorkplaceCommand command) {
        errorStatus = null;
        validateName(command.name(), command.companyName());
        validateAddress(command.address());
        validateDescription(command.description());
        validateLocation(command.location());
    }

    public void validate(UpdateWorkplaceCommand command) {
        errorStatus = null;
        validateName(command.name(), command.companyName());
        validateDescription(command.description());
    }

    private void validateName(String name, String companyName) {
        if (Strings.isBlank(name)) {
            errorStatus = ErrorStatus.WORKPLACE_NAME_NULL;
        } else if (repository.findByNameAndCompanyName(name, companyName).isPresent()) {
            errorStatus = ErrorStatus.WORKPLACE_NAME_TAKEN;
        }
        throwExceptionOnErrors();
    }

    private void validateAddress(String address) {
        if (Strings.isBlank(address)) {
            errorStatus = ErrorStatus.WORKPLACE_ADDRESS_NULL;
        }
        throwExceptionOnErrors();
    }

    private void validateDescription(String description) {
        if (Strings.isBlank(description)) {
            errorStatus = ErrorStatus.WORKPLACE_DESCRIPTION_NULL;
        }
        throwExceptionOnErrors();
    }

    private void validateLocation(WorkplaceLocation location) {
        if (location == null) {
            throw new ValidationException(ErrorStatus.WORKPLACE_LOCATION_NULL);
        }
        double latitude = location.latitude();
        double longitude = location.longitude();
        if (latitude == 0.0) {
            errorStatus = ErrorStatus.WORKPLACE_LATITUDE_CANNOT_BE_ZERO;
        } else if (longitude == 0.0) {
            errorStatus = ErrorStatus.WORKPLACE_LONGITUDE_CANNOT_BE_ZERO;
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
