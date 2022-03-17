package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;

public interface UserStrategy {

    boolean isApplicableFor(RoleType roleType);

    UserAggregate processCreation(CreateUserDto dto);

    UserAggregate processUpdate(UpdateUserCommand command);
}
