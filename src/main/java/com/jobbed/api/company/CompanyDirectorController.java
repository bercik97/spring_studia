package com.jobbed.api.company;

import com.jobbed.api.company.domain.dto.CompanyUsersDto;
import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.shared.pageable.PageResponse;
import com.jobbed.api.shared.pageable.PageUtil;
import com.jobbed.api.token.domain.TokenFacade;
import com.jobbed.api.token.domain.dto.TokenDto;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.UserFacade;
import com.jobbed.api.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/director/company")
@RequiredArgsConstructor
public class CompanyDirectorController {

    private final TokenFacade tokenFacade;
    private final UserFacade userFacade;

    @GetMapping("/tokens-with-users")
    public CompanyUsersDto findTokensAndUsersByCompanyName(Authentication authentication, Pageable pageable) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        List<TokenDto> tokens = tokenFacade.findAllByCompanyName(user.getCompanyName());
        PageResponse<UserDto> users = PageUtil.toPageResponse(userFacade.findAllByCompanyNameExceptMe(pageable, user.getCompanyName(), user.getId()));
        return new CompanyUsersDto(tokens, users);
    }
}
