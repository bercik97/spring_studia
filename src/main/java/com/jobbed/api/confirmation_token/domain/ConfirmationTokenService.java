package com.jobbed.api.confirmation_token.domain;

import com.jobbed.api.confirmation_token.domain.command.CreateConfirmationTokenCommand;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenVO;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenUuid create(CreateConfirmationTokenCommand command) {
        ConfirmationTokenUuid confirmationTokenUuid = createRandomUuid();
        repository.save(ConfirmationTokenFactory.create(confirmationTokenUuid, command));
        return confirmationTokenUuid;
    }

    private ConfirmationTokenUuid createRandomUuid() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (repository.findByUuid(uuid).isPresent());
        return new ConfirmationTokenUuid(uuid);
    }

    public ConfirmationTokenVO confirm(ConfirmationTokenUuid confirmationTokenUuid) {
        Optional<ConfirmationTokenAggregate> byUuid = repository.findByUuid(confirmationTokenUuid.uuid());
        if (byUuid.isEmpty()) {
            return null;
        }
        ConfirmationTokenAggregate confirmationToken = byUuid.get();
        boolean isAlreadyConfirmed = Objects.nonNull(confirmationToken.getConfirmedDate());
        if (isAlreadyConfirmed) {
            return null;
        }
        repository.updateConfirmedDate(confirmationToken, ZonedDateTime.now());
        return new ConfirmationTokenVO(confirmationTokenUuid, confirmationToken.getId());
    }
}
