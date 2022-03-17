package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.company.domain.CompanyAggregate;
import com.jobbed.api.company.domain.CompanyFacade;
import com.jobbed.api.company.domain.command.CreateCompanyCommand;
import com.jobbed.api.role.RoleType;
import com.jobbed.api.token.domain.TokenFacade;
import com.jobbed.api.token.domain.command.CreateTokenCommand;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DirectorStrategyImpl implements UserStrategy {

    private static final RoleType ROLE_DIRECTOR = RoleType.ROLE_DIRECTOR;

    private final CompanyFacade companyFacade;
    private final TokenFacade tokenFacade;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isApplicableFor(RoleType roleType) {
        return roleType == ROLE_DIRECTOR;
    }

    @Override
    public UserAggregate processCreation(CreateUserDto dto) {
        CompanyAggregate company = companyFacade.create(new CreateCompanyCommand(dto.companyName()));
        tokenFacade.create(new CreateTokenCommand(RoleType.ROLE_EMPLOYEE, company.getName()));

        return UserFactory.create(dto, passwordEncoder.encode(dto.password()), company, ROLE_DIRECTOR.create());
    }

    @Override
    public UserAggregate processUpdate(UpdateUserCommand command) {
        return command.userAggregate();
    }
}

