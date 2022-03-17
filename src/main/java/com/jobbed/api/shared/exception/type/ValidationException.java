package com.jobbed.api.shared.exception.type;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.ApplicationException;

public class ValidationException extends ApplicationException {

    public ValidationException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
