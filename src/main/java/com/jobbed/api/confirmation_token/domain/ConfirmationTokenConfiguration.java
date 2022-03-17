package com.jobbed.api.confirmation_token.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
class ConfirmationTokenConfiguration {

    @Bean
    public ConfirmationTokenFacade confirmationTokenFacade(ConfirmationTokenRepository repository) {
        ConfirmationTokenService service = new ConfirmationTokenService(repository);
        return new ConfirmationTokenFacade(service);
    }

    public ConfirmationTokenFacade confirmationTokenFacade(ConcurrentHashMap<Long, ConfirmationTokenEntity> db) {
        ConfirmationTokenInMemoryRepository repository = new ConfirmationTokenInMemoryRepository(db);
        ConfirmationTokenService service = new ConfirmationTokenService(repository);
        return new ConfirmationTokenFacade(service);
    }
}
