package com.jobbed.api.token.domain;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import com.jobbed.api.token.domain.command.CreateTokenCommand;
import com.jobbed.api.token.domain.dto.TokenDto;
import com.jobbed.api.token.domain.vo.TokenCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class TokenService {

    private final TokenRepository repository;

    public void create(CreateTokenCommand command) {
        String code = generateCode();
        repository.save(TokenFactory.create(code, command));
    }

    public TokenCode resetByCodeAndCompanyName(String code, String companyName) {
        TokenAggregate token = repository.findByCodeAndCompanyName(code, companyName).orElseThrow(() -> new NotFoundException(ErrorStatus.TOKEN_NOT_FOUND));
        TokenCode newCode = new TokenCode(generateCode());
        repository.updateCodeById(newCode, token.getId());
        return newCode;
    }

    private String generateCode() {
        String code;
        do {
            code = RandomStringUtils.randomNumeric(6);
        } while (findByCode(code).isPresent());
        return code;
    }

    public Optional<TokenAggregate> findByCode(String name) {
        return repository.findByCode(name);
    }

    public List<TokenDto> findAllByCompanyName(String companyName) {
        return repository.findAllByCompanyName(companyName)
                .stream()
                .map(TokenDto::of)
                .toList();
    }
}
