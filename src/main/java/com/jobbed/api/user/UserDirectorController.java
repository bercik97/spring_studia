package com.jobbed.api.user;

import com.jobbed.api.security.model.CustomUserDetails;
import com.jobbed.api.user.domain.UserAggregate;
import com.jobbed.api.user.domain.UserFacade;
import com.jobbed.api.user.domain.dto.UpdateUserDto;
import com.jobbed.api.user.domain.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/director/users")
@RequiredArgsConstructor
class UserDirectorController {

    private final UserFacade facade;

    @GetMapping("{id}")
    public UserDetailsDto findByIdAndCompanyName(Authentication authentication, @PathVariable long id) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        return facade.findByIdAndCompanyName(id, user.getCompanyName());
    }

    @GetMapping("{email}")
    public UserDetailsDto findByEmailAndCompanyName(Authentication authentication, @PathVariable String email) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        return facade.findByEmailAndCompanyName(email, user.getCompanyName());
    }

    @PutMapping("{id}")
    public void updateByIdAndCompanyName(Authentication authentication, @PathVariable long id, @RequestBody UpdateUserDto dto) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        facade.updateByIdAndCompanyName(id, user.getCompanyName(), dto);
    }

    @DeleteMapping("{id}")
    public void deleteByIdAndCompanyName(Authentication authentication, @PathVariable long id) {
        UserAggregate user = ((CustomUserDetails) authentication.getPrincipal()).getAggregate();
        facade.deleteByIdAndCompanyName(id, user.getCompanyName());
    }
}
