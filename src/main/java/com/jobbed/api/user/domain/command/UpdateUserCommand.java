package com.jobbed.api.user.domain.command;

import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.dto.UpdateUserDto;

public record UpdateUserCommand(long id,
                                String companyName,
                                UserAggregate userAggregate,
                                String name, String surname, String phoneNumber, String phoneNumberCode) {

    public static UpdateUserCommand of(long id, String companyName, UserAggregate userAggregate, UpdateUserDto dto) {
        return new UpdateUserCommand(id, companyName, userAggregate, dto.name(), dto.surname(), dto.phoneNumber(), dto.phoneNumberCode());
    }
}
