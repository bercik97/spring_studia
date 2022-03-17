package com.jobbed.api.token.domain;

import com.jobbed.api.token.domain.vo.TokenCode;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    void save(TokenAggregate token);

    Optional<TokenAggregate> findByCode(String name);

    Optional<TokenAggregate> findByCodeAndCompanyName(String code, String companyName);

    List<TokenAggregate> findAllByCompanyName(String companyName);

    void updateCodeById(TokenCode newCode, long id);
}
