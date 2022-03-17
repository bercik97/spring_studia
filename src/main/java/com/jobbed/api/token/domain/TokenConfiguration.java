package com.jobbed.api.token.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
class TokenConfiguration {

    @Bean
    public TokenFacade tokenFacade(TokenRepository repository) {
        TokenService service = new TokenService(repository);
        return new TokenFacade(service);
    }

    public TokenFacade tokenFacade(ConcurrentHashMap<Long, TokenEntity> db) {
        TokenInMemoryRepository repository = new TokenInMemoryRepository(db);
        TokenService service = new TokenService(repository);
        return new TokenFacade(service);
    }
}
