package com.jobbed.api.user;

import com.jobbed.api.user.domain.UserFacade;
import com.jobbed.api.user.domain.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unauthenticated/users")
@RequiredArgsConstructor
class UserUnauthenticatedController {

    private final UserFacade facade;

    @PostMapping
    public void create(@RequestBody CreateUserDto dto) {
        facade.create(dto);
    }
}
