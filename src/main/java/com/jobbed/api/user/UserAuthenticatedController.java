package com.jobbed.api.user;

import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.UserFacade;
import com.jobbed.api.user.domain.command.UpdatePasswordCommand;
import com.jobbed.api.user.domain.dto.UpdatePasswordDto;
import com.jobbed.api.user.domain.dto.UpdateUserDto;
import com.jobbed.api.user.domain.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticated/users")
@RequiredArgsConstructor
class UserAuthenticatedController {

    private final UserFacade facade;

    @GetMapping("/me")
    public UserDetailsDto findMe(Authentication authentication) {
        return UserDetailsDto.from(((CustomUserDetails) authentication.getPrincipal()).getAggregate());
    }

    @PatchMapping("/me/password")
    public void updatePassword(@RequestBody UpdatePasswordDto dto, Authentication authentication) {
        final String currentHashedPassword = ((CustomUserDetails) authentication.getPrincipal()).getPassword();
        final String email = ((CustomUserDetails) authentication.getPrincipal()).getUsername();
        facade.updatePassword(UpdatePasswordCommand.of(email, currentHashedPassword, dto));
    }

    @PutMapping("/me")
    public void update(@RequestBody UpdateUserDto dto, Authentication authentication) {
        final UserAggregate userAggregate = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        facade.updateByIdAndCompanyName(userAggregate.getId(), userAggregate.getCompanyName(), dto);
    }
}
