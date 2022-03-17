package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.role.RoleAggregate;
import com.jobbed.api.role.RoleType;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserStrategyService {

    private final UserStrategyFactory userStrategyFactory;

    public UserAggregate processCreation(CreateUserDto dto) {
        UserStrategy strategy = userStrategyFactory.getStrategyFor(dto.roleType());
        return strategy.processCreation(dto);
    }

    public UserAggregate processUpdate(UpdateUserCommand command, Set<RoleAggregate> roles) {
        Optional<RoleAggregate> role = roles.stream().findFirst();
        if (role.isEmpty()) {
            throw new NotFoundException(ErrorStatus.USER_ROLE_NULL);
        }
        UserStrategy strategy = userStrategyFactory.getStrategyFor(RoleType.valueOf(role.get().getRole()));
        return strategy.processUpdate(command);
    }
}
