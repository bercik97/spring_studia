package com.jobbed.api.confirmation_token.domain;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository {

    void save(ConfirmationTokenAggregate confirmationToken);

    void updateConfirmedDate(ConfirmationTokenAggregate confirmationToken, ZonedDateTime confirmedDate);

    Optional<ConfirmationTokenAggregate> findByUuid(String uuid);
}
