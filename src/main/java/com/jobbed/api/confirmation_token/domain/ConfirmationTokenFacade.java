package com.jobbed.api.confirmation_token.domain;

import com.jobbed.api.confirmation_token.domain.command.CreateConfirmationTokenCommand;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenVO;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
public class ConfirmationTokenFacade {

    private final ConfirmationTokenService service;

    public ConfirmationTokenUuid create(CreateConfirmationTokenCommand command) {
        return service.create(command);
    }

    public ConfirmationTokenVO confirm(ConfirmationTokenUuid confirmationTokenUuid) {
        return service.confirm(confirmationTokenUuid);
    }
}
