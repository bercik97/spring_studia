package com.jobbed.api.token.domain;

import com.jobbed.api.token.domain.vo.TokenCode;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

record TokenInMemoryRepository(ConcurrentHashMap<Long, TokenEntity> db) implements TokenRepository {

    private static long idCounter = 0;

    @Override
    public void save(TokenAggregate token) {
        token.setId(++idCounter);
        db.put(idCounter, token.toEntity());
    }

    @Override
    public Optional<TokenAggregate> findByCode(String code) {
        return db.values()
                .stream()
                .filter(token -> code.equals(token.getCode()))
                .map(TokenEntity::toAggregate)
                .findFirst();
    }

    @Override
    public Optional<TokenAggregate> findByCodeAndCompanyName(String code, String companyName) {
        return db.values()
                .stream()
                .filter(token -> code.equals(token.getCode()))
                .filter(token -> companyName.equals(token.getCompanyName()))
                .map(TokenEntity::toAggregate)
                .findFirst();
    }

    @Override
    public List<TokenAggregate> findAllByCompanyName(String companyName) {
        return db.values()
                .stream()
                .filter(token -> companyName.equals(token.getCompanyName()))
                .map(TokenEntity::toAggregate)
                .toList();
    }

    @Override
    public void updateCodeById(TokenCode newCode, long id) {
        db.values()
                .stream()
                .filter(token -> id == token.getId())
                .map(TokenEntity::toAggregate)
                .findFirst()
                .ifPresent(token -> {
                    token.setCode(newCode.code());
                    db.put(token.getId(), token.toEntity());
                });
    }
}
