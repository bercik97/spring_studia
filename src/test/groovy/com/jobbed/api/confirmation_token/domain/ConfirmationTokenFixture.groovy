package com.jobbed.api.confirmation_token.domain

import com.jobbed.api.confirmation_token.domain.command.CreateConfirmationTokenCommand

trait ConfirmationTokenFixture {

    static ConfirmationTokenEntity createConfirmationTokenEntity() {
        return new ConfirmationTokenEntity(
                UUID.randomUUID().toString(),
                1L,
                ConfirmationTokenType.ACCOUNT_CONFIRMATION,
                null)
    }

    static CreateConfirmationTokenCommand createConfirmationTokenCommand(def userId = 1L, def tokenType = ConfirmationTokenType.ACCOUNT_CONFIRMATION) {
        return new CreateConfirmationTokenCommand(userId, tokenType)
    }
}
