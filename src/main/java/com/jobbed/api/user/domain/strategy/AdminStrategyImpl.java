package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AdminStrategyImpl implements UserStrategy {

    private static final RoleType ROLE_ADMIN = RoleType.ROLE_ADMIN;

    @Override
    public boolean isApplicableFor(RoleType roleType) {
        return roleType == ROLE_ADMIN;
    }

    @Override
    public UserAggregate processCreation(CreateUserDto dto) {
        throw new UnsupportedOperationException("To be implemented ...");
    }

    @Override
    public UserAggregate processUpdate(UpdateUserCommand command) {
        return command.userAggregate();
    }
}
