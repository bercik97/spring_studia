package com.jobbed.api.token;

import com.jobbed.api.token.domain.TokenFacade;
import com.jobbed.api.token.domain.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unauthenticated/tokens")
@RequiredArgsConstructor
class TokenUnauthenticatedController {

    private final TokenFacade facade;

    @GetMapping("{code}")
    public TokenDto findByCode(@PathVariable String code) {
        return TokenDto.of(facade.findByCode(code));
    }
}
