package com.jobbed.api.confirmation_token.domain;

import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
class ConfirmationTokenInMemoryRepository implements ConfirmationTokenRepository {

    private final ConcurrentHashMap<Long, ConfirmationTokenEntity> db;

    private static long idCounter = 0;

    @Override
    public void save(ConfirmationTokenAggregate confirmationToken) {
        confirmationToken.setId(++idCounter);
        db.put(idCounter, confirmationToken.toEntity());
    }

    @Override
    public void updateConfirmedDate(ConfirmationTokenAggregate confirmationToken, ZonedDateTime confirmedDate) {
        db.values()
                .stream()
                .filter(ct -> ct.getId() == confirmationToken.getId())
                .findFirst()
                .ifPresent(ct -> {
                    ct.setConfirmedDate(confirmedDate);
                    db.put(ct.getId(), ct);
                });
    }

    @Override
    public Optional<ConfirmationTokenAggregate> findByUuid(String uuid) {
        return db.values()
                .stream()
                .filter(ct -> ct.getUuid().equals(uuid))
                .findFirst()
                .map(ConfirmationTokenEntity::toAggregate);
    }
}
