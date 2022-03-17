package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.company.domain.CompanyAggregate;
import com.jobbed.api.company.domain.CompanyFacade;
import com.jobbed.api.role.RoleType;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import com.jobbed.api.token.domain.TokenAggregate;
import com.jobbed.api.token.domain.TokenFacade;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EmployeeStrategyImpl implements UserStrategy {

    private static final RoleType ROLE_EMPLOYEE = RoleType.ROLE_EMPLOYEE;

    private final CompanyFacade companyFacade;
    private final TokenFacade tokenFacade;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isApplicableFor(RoleType roleType) {
        return roleType == ROLE_EMPLOYEE;
    }

    @Override
    public UserAggregate processCreation(CreateUserDto dto) {
        CompanyAggregate company = companyFacade.findByName(dto.companyName());
        TokenAggregate token = tokenFacade.findByCode(dto.tokenCode());

        validate(dto, company, token);

        return UserFactory.create(dto, passwordEncoder.encode(dto.password()), company, ROLE_EMPLOYEE.create());
    }

    @Override
    public UserAggregate processUpdate(UpdateUserCommand command) {
        validatePhone(command.phoneNumber(), command.phoneNumberCode());

        return UserFactory.update(command.userAggregate(), command);
    }

    private void validate(CreateUserDto dto, CompanyAggregate company, TokenAggregate token) {
        validatePhone(dto.phoneNumber(), dto.phoneNumberCode());

        boolean isCompanyNameMatch = company.getName().equals(dto.companyName());
        if (!isCompanyNameMatch) {
            throw new ValidationException(ErrorStatus.TOKEN_NOT_MATCH_WITH_COMPANY);
        }
        boolean isTokenCompanyNameMatch = token.getCompanyName().equals(company.getName());
        if (!isTokenCompanyNameMatch) {
            throw new ValidationException(ErrorStatus.TOKEN_NOT_MATCH_WITH_COMPANY);
        }
        boolean isTokenRoleMatch = token.getRoleType() == ROLE_EMPLOYEE;
        if (!isTokenRoleMatch) {
            throw new ValidationException(ErrorStatus.TOKEN_NOT_MATCH_WITH_COMPANY);
        }
    }

    private void validatePhone(String phoneNumber, String phoneNumberCode) {
        if (Strings.isBlank(phoneNumber)) {
            throw new ValidationException(ErrorStatus.USER_PHONE_NUMBER_NULL);
        } else if (Strings.isBlank(phoneNumberCode)) {
            throw new ValidationException(ErrorStatus.USER_PHONE_NUMBER_CODE_NULL);
        }
    }
}
