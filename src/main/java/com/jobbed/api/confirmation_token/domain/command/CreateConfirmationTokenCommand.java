package com.jobbed.api.confirmation_token.domain.command;

import com.jobbed.api.confirmation_token.domain.ConfirmationTokenType;

public record CreateConfirmationTokenCommand(long userId, ConfirmationTokenType tokenType) {
}
