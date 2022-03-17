package com.jobbed.api.shared.exception;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import com.jobbed.api.shared.exception.type.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_CODE = "errorCode";

    @ExceptionHandler({NotFoundException.class, ValidationException.class})
    public ResponseEntity<Map<String, Integer>> handle(ApplicationException exception) {
        ErrorStatus errorStatus = exception.getErrorStatus();
        log.warn("{} has been thrown with error status: {}", exception, errorStatus);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(ERROR_CODE, errorStatus.getCode()));
    }
}
