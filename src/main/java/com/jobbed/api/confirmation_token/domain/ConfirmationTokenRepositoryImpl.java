package com.jobbed.api.confirmation_token.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ConfirmationTokenRepositoryImpl implements ConfirmationTokenRepository {

    private final ConfirmationTokenJpaRepository jpaRepository;

    @Override
    public void save(ConfirmationTokenAggregate confirmationToken) {
        jpaRepository.save(confirmationToken.toEntity());
    }

    @Override
    public void updateConfirmedDate(ConfirmationTokenAggregate confirmationToken, ZonedDateTime confirmedDate) {
        jpaRepository.updateConfirmedDateById(confirmedDate, confirmationToken.getId());
    }

    @Override
    public Optional<ConfirmationTokenAggregate> findByUuid(String uuid) {
        return jpaRepository.findByUuid(uuid).map(ConfirmationTokenEntity::toAggregate);
    }
}
