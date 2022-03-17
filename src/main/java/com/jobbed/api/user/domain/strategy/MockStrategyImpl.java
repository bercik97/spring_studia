package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MockStrategyImpl implements UserStrategy {

    private static long idCounter = 0;

    @Override
    public boolean isApplicableFor(RoleType roleType) {
        return true;
    }

    @Override
    public UserAggregate processCreation(CreateUserDto dto) {
        return new UserAggregate(
                ++idCounter,
                ZonedDateTime.now(),
                dto.name(),
                dto.surname(),
                dto.email(),
                dto.phoneNumber(),
                dto.phoneNumberCode(),
                dto.password(),
                dto.companyName(),
                false,
                Set.of(dto.roleType().create()));
    }

    @Override
    public UserAggregate processUpdate(UpdateUserCommand command) {
        return UserFactory.update(command.userAggregate(), command);
    }
}
