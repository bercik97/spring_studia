package com.jobbed.api.token.domain

import com.jobbed.api.role.RoleType
import com.jobbed.api.token.domain.command.CreateTokenCommand

trait TokenFixture {

    static TokenEntity createTokenEntity(def code = '111111', def companyName = 'Jobbed') {
        return new TokenEntity(code, RoleType.ROLE_DIRECTOR, companyName)
    }

    static CreateTokenCommand createTokenCommand(RoleType roleType, def companyName = 'Jobbed') {
        return new CreateTokenCommand(roleType, companyName)
    }
}
