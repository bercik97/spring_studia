package com.jobbed.api.shared.exception.type;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.ApplicationException;

public class NotFoundException extends ApplicationException {

    public NotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
