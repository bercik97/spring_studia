package com.jobbed.api.user.domain.strategy;

import com.jobbed.api.company.domain.CompanyAggregate;
import com.jobbed.api.role.RoleAggregate;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.command.UpdateUserCommand;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserFactory {

    public static UserAggregate create(CreateUserDto dto, String encodedPassword, CompanyAggregate companyAggregate, RoleAggregate roleAggregate) {
        return UserAggregate.builder()
                .name(dto.name())
                .surname(dto.surname())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .phoneNumberCode(dto.phoneNumberCode())
                .password(encodedPassword)
                .companyName(companyAggregate.getName())
                .isEnabled(false)
                .roles(Set.of(roleAggregate))
                .build();
    }

    public static UserAggregate update(UserAggregate userAggregate, UpdateUserCommand command) {
        userAggregate.setName(command.name());
        userAggregate.setSurname(command.surname());
        userAggregate.setPhoneNumber(command.phoneNumber());
        userAggregate.setPhoneNumberCode(command.phoneNumberCode());
        return userAggregate;
    }
}
