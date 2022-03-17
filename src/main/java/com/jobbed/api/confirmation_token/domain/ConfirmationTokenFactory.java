package com.jobbed.api.confirmation_token.domain;

import com.jobbed.api.confirmation_token.domain.command.CreateConfirmationTokenCommand;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ConfirmationTokenFactory {

    public static ConfirmationTokenAggregate create(ConfirmationTokenUuid confirmationTokenUuid, CreateConfirmationTokenCommand command) {
        return ConfirmationTokenAggregate.builder()
                .uuid(confirmationTokenUuid.uuid())
                .userId(command.userId())
                .tokenType(command.tokenType())
                .build();
    }
}
