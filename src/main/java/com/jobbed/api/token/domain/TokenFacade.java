package com.jobbed.api.token.domain;

import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.NotFoundException;
import com.jobbed.api.token.domain.command.CreateTokenCommand;
import com.jobbed.api.token.domain.dto.TokenDto;
import com.jobbed.api.token.domain.vo.TokenCode;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public class TokenFacade {

    private final TokenService service;

    public void create(CreateTokenCommand command) {
        service.create(command);
    }

    public TokenAggregate findByCode(String code) {
        return service.findByCode(code).orElseThrow(() -> new NotFoundException(ErrorStatus.TOKEN_NOT_FOUND));
    }

    public List<TokenDto> findAllByCompanyName(String companyName) {
        return service.findAllByCompanyName(companyName);
    }

    public TokenCode resetByCodeAndCompanyName(String code, String companyName) {
        return service.resetByCodeAndCompanyName(code, companyName);
    }
}
