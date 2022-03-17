package com.jobbed.api.user.domain.dto;

import com.jobbed.api.user.domain.UserAggregate;

public record UserDto(long id, String name, String surname, boolean isEnabled) {

    public static UserDto from(UserAggregate userAggregate) {
        return new UserDto(userAggregate.getId(), userAggregate.getName(), userAggregate.getSurname(), userAggregate.isEnabled());
    }
}
