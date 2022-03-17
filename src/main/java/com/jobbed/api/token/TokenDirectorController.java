package com.jobbed.api.token;

import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.token.domain.TokenFacade;
import com.jobbed.api.token.domain.vo.TokenCode;
import com.jobbed.api.user.domain.UserAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/director/tokens")
@RequiredArgsConstructor
class TokenDirectorController {

    private final TokenFacade facade;

    @PatchMapping("/reset/{code}")
    public TokenCode resetByCodeAndCompanyName(Authentication authentication, @PathVariable String code) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        return facade.resetByCodeAndCompanyName(code, user.getCompanyName());
    }
}
