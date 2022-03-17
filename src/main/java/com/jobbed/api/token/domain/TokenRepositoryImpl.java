package com.jobbed.api.token.domain;

import com.jobbed.api.token.domain.vo.TokenCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository jpaRepository;

    @Override
    public void save(TokenAggregate token) {
        jpaRepository.save(token.toEntity());
    }

    @Override
    public Optional<TokenAggregate> findByCode(String name) {
        return jpaRepository.findByCode(name).map(TokenEntity::toAggregate);
    }

    @Override
    public Optional<TokenAggregate> findByCodeAndCompanyName(String name, String companyName) {
        return jpaRepository.findByCodeAndCompanyName(name, companyName).map(TokenEntity::toAggregate);
    }

    @Override
    public List<TokenAggregate> findAllByCompanyName(String companyName) {
        return jpaRepository.findAllByCompanyName(companyName)
                .stream()
                .map(TokenEntity::toAggregate)
                .toList();
    }

    @Override
    public void updateCodeById(TokenCode newCode, long id) {
        jpaRepository.setCodeById(newCode.code(), id);
    }
}
