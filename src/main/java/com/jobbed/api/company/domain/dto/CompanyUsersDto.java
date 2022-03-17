package com.jobbed.api.company.domain.dto;

import com.jobbed.api.shared.pageable.PageResponse;
import com.jobbed.api.token.domain.dto.TokenDto;
import com.jobbed.api.user.domain.dto.UserDto;

import java.util.List;

public record CompanyUsersDto(List<TokenDto> tokens, PageResponse<UserDto> users) {
}
