package com.jobbed.api.token.domain

import com.jobbed.api.role.RoleType
import com.jobbed.api.shared.error.ErrorStatus
import com.jobbed.api.shared.exception.type.NotFoundException
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class TokenSpec extends Specification implements TokenFixture {

    private TokenFacade tokenFacade

    private ConcurrentHashMap<Long, TokenEntity> db

    def setup() {
        db = new ConcurrentHashMap<>()
        tokenFacade = new TokenConfiguration().tokenFacade(db)
    }

    def 'Should create token'() {
        given:
        def command = createTokenCommand(RoleType.ROLE_EMPLOYEE)

        when:
        tokenFacade.create(command)

        then:
        db.size() == 1 && db.get(1L).roleType == command.roleType
    }

    def 'Should find token by code'() {
        given:
        def code = '111111'
        db.put(1L, createTokenEntity(code))

        when:
        def foundToken = tokenFacade.findByCode(code)

        then:
        foundToken != null && foundToken.code == code
    }

    def 'Should throw an exception cause cannot find token by code'() {
        given:
        def code = '111111'
        db.put(1L, createTokenEntity(code))

        when:
        tokenFacade.findByCode('definitelyWrongCode')

        then:
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.TOKEN_NOT_FOUND
    }

    def 'Should find all tokens by company name'() {
        given:
        def code = '111111'
        def companyName = 'Jobbed'
        db.put(1L, createTokenEntity(code, companyName))

        when:
        def foundTokens = tokenFacade.findAllByCompanyName(companyName)

        then:
        !foundTokens.isEmpty() && foundTokens.size() == db.size()
    }

    def 'Should reset token code by code and company name'() {
        given:
        def oldCode = '111111'
        def companyName = 'Jobbed'
        db.put(1L, createTokenEntity(oldCode, companyName))

        when:
        def newCode = tokenFacade.resetByCodeAndCompanyName(oldCode, companyName)

        then:
        tokenFacade.findByCode(newCode.code).code != oldCode
    }

    def 'Should throw an exception cause token do not exists'() {
        when:
        tokenFacade.resetByCodeAndCompanyName('nonExistingCode', 'nonExistingCompanyName')

        then:
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.TOKEN_NOT_FOUND
    }
}
