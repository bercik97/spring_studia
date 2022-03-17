package com.jobbed.api.confirmation_token.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.Optional;

interface ConfirmationTokenJpaRepository extends JpaRepository<ConfirmationTokenEntity, Long> {

    @Modifying
    @Query("update ConfirmationTokenEntity ct set ct.confirmedDate = ?1 where ct.id = ?2")
    void updateConfirmedDateById(ZonedDateTime confirmedDate, long id);

    Optional<ConfirmationTokenEntity> findByUuid(String uuid);
}
