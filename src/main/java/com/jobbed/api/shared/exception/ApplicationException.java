package com.jobbed.api.shared.exception;

import com.jobbed.api.shared.error.ErrorStatus;
import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public ApplicationException(ErrorStatus errorStatus) {
        super();
        this.errorStatus = errorStatus;
    }
}

