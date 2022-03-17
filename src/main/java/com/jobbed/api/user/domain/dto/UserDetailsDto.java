package com.jobbed.api.user.domain.dto;

import com.jobbed.api.role.RoleAggregate;
import com.jobbed.api.user.domain.UserAggregate;

import java.time.ZonedDateTime;
import java.util.Set;

public record UserDetailsDto(long id,
                             ZonedDateTime createdDate,
                             String name,
                             String surname,
                             String email,
                             String phoneNumber,
                             String phoneNumberCode,
                             String companyName,
                             boolean isEnabled,
                             Set<RoleAggregate> roles) {

    public static UserDetailsDto from(UserAggregate user) {
        return new UserDetailsDto(
                user.getId(),
                user.getCreatedDate(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPhoneNumberCode(),
                user.getCompanyName(),
                user.isEnabled(),
                user.getRoles());
    }
}
