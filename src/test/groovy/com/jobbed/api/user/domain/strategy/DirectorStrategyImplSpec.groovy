package com.jobbed.api.user.domain.strategy

import com.jobbed.api.company.domain.CompanyAggregate
import com.jobbed.api.company.domain.CompanyFacade
import com.jobbed.api.role.RoleType
import com.jobbed.api.token.domain.TokenFacade
import com.jobbed.api.user.domain.UserFixture
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class DirectorStrategyImplSpec extends Specification implements UserFixture {

    private final static RoleType ROLE_DIRECTOR = RoleType.ROLE_DIRECTOR

    private DirectorStrategyImpl directorCreator

    private CompanyFacade companyFacade = Mock(CompanyFacade)

    private TokenFacade tokenFacade = Mock(TokenFacade)

    def setup() {
        directorCreator = new DirectorStrategyImpl(companyFacade, tokenFacade, Mock(PasswordEncoder))
    }

    def 'Should process DIRECTOR creation'() {
        given:
        def dto = createUserDto('john.doe@mail.com', '12345!aA', ROLE_DIRECTOR)

        when:
        def user = directorCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.create(_) >> new CompanyAggregate()
        1 * tokenFacade.create(_)

        and: 'created user contains role DIRECTOR'
        user != null
        user.roles.stream().filter({ role -> role.id == ROLE_DIRECTOR.id && role.role == ROLE_DIRECTOR.name })
    }
}
