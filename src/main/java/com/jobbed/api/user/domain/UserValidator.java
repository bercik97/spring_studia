package com.jobbed.api.user.domain;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import com.jobbed.api.user.domain.command.UpdatePasswordCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import com.jobbed.api.user.domain.dto.UpdateUserDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

class UserValidator {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$");

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private ErrorStatus errorStatus;

    public UserValidator(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validate(CreateUserDto dto) {
        errorStatus = null;
        validateName(dto.name(), dto.surname());
        validateEmail(dto.email());
        validatePassword(dto.password());
        validateRePassword(dto.password(), dto.rePassword());
        validateRole(dto.roleType());
    }

    public void validate(UpdatePasswordCommand command) {
        errorStatus = null;
        validateOldPassword(command.currentPassword(), command.oldPassword());
        validatePassword(command.newPassword());
        validateRePassword(command.newPassword(), command.reNewPassword());
    }

    public void validate(UpdateUserDto dto) {
        errorStatus = null;
        validateName(dto.name(), dto.surname());
    }

    private void validateName(String name, String surname) {
        if (Strings.isBlank(name)) {
            errorStatus = ErrorStatus.USER_NAME_NULL;
        } else if (Strings.isBlank(surname)) {
            errorStatus = ErrorStatus.USER_SURNAME_NULL;
        }
        throwExceptionOnErrors();
    }

    private void validateEmail(String email) {
        if (Strings.isBlank(email)) {
            errorStatus = ErrorStatus.USER_EMAIL_NULL;
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            errorStatus = ErrorStatus.USER_EMAIL_WRONG_FORMAT;
        } else if (repository.findByEmail(email).isPresent()) {
            errorStatus = ErrorStatus.USER_EMAIL_TAKEN;
        }
        throwExceptionOnErrors();
    }

    private void validateOldPassword(String currentPassword, String oldPassword) {
        boolean isOldPasswordMatchWithCurrentPassword = passwordEncoder.matches(oldPassword, currentPassword);
        if (!isOldPasswordMatchWithCurrentPassword) {
            errorStatus = ErrorStatus.USER_OLD_PASSWORD_DO_NOT_MATCH_WITH_CURRENT_PASSWORD;
        }
        throwExceptionOnErrors();
    }

    private void validatePassword(String password) {
        if (Strings.isBlank(password)) {
            errorStatus = ErrorStatus.USER_PASSWORD_NULL;
        } else if (!PASSWORD_REGEX.matcher(password).matches()) {
            errorStatus = ErrorStatus.USER_PASSWORD_NOT_SAFE;
        }
        throwExceptionOnErrors();
    }

    private void validateRePassword(String password, String rePassword) {
        if (Strings.isBlank(rePassword)) {
            errorStatus = ErrorStatus.USER_RE_PASSWORD_NULL;
        } else if (!password.equals(rePassword)) {
            errorStatus = ErrorStatus.USER_RE_PASSWORD_DO_NOT_MATCH_PASSWORD;
        }
        throwExceptionOnErrors();
    }

    private void validateRole(RoleType roleType) {
        if (roleType == null) {
            errorStatus = ErrorStatus.USER_ROLE_NULL;
        } else if (roleType == RoleType.ROLE_ADMIN) {
            errorStatus = ErrorStatus.USER_ROLE_CANNOT_BE_ADMIN;
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
