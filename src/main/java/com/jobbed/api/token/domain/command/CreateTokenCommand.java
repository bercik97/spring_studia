package com.jobbed.api.token.domain.command;

import com.jobbed.api.role.RoleType;

public record CreateTokenCommand(RoleType roleType, String companyName) {
}
