package com.jobbed.api.user.domain.strategy

import com.jobbed.api.company.domain.CompanyAggregate
import com.jobbed.api.company.domain.CompanyFacade
import com.jobbed.api.role.RoleType
import com.jobbed.api.shared.error.ErrorStatus
import com.jobbed.api.shared.exception.type.ValidationException
import com.jobbed.api.token.domain.TokenAggregate
import com.jobbed.api.token.domain.TokenFacade
import com.jobbed.api.user.domain.UserAggregate
import com.jobbed.api.user.domain.UserFixture
import com.jobbed.api.user.domain.command.UpdateUserCommand
import com.jobbed.api.user.domain.dto.CreateUserDto
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class EmployeeStrategyImplSpec extends Specification implements UserFixture {

    private final static RoleType ROLE_EMPLOYEE = RoleType.ROLE_EMPLOYEE

    private EmployeeStrategyImpl employeeCreator

    private CompanyFacade companyFacade = Mock(CompanyFacade)

    private TokenFacade tokenFacade = Mock(TokenFacade)

    def setup() {
        employeeCreator = new EmployeeStrategyImpl(companyFacade, tokenFacade, Mock(PasswordEncoder))
    }

    def 'Should process EMPLOYEE creation'() {
        given:
        def companyName = 'Jobbed'
        def code = '111111'
        def dto = new CreateUserDto(
                'John', 'Doe', 'john.doe@mail.com', '+48123456789', 'PL',
                '12345!aA', '12345aA!',
                companyName, code, ROLE_EMPLOYEE)
        def company = new CompanyAggregate()
        def token = new TokenAggregate()
        company.name = companyName
        token.companyName = companyName
        token.code = code
        token.roleType = ROLE_EMPLOYEE

        when:
        def user = employeeCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.findByName(_) >> company
        1 * tokenFacade.findByCode(_) >> token

        and: 'created user contains role EMPLOYEE'
        user != null
        user.roles.stream().filter({ role -> role.id == ROLE_EMPLOYEE.id && role.role == ROLE_EMPLOYEE.name })
    }

    def 'Should throw an exception while process EMPLOYEE creation cause phone number is empty'() {
        given:
        def companyName = 'Jobbed'
        def code = '111111'
        def phoneNumber = null
        def dto = new CreateUserDto(
                'John', 'Doe', 'john.doe@mail.com', phoneNumber, 'PL',
                '12345!aA', '12345aA!',
                companyName, code, ROLE_EMPLOYEE)
        def company = new CompanyAggregate()
        def token = new TokenAggregate()
        company.name = companyName
        token.companyName = companyName
        token.code = code
        token.roleType = ROLE_EMPLOYEE

        when:
        employeeCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.findByName(_) >> company
        1 * tokenFacade.findByCode(_) >> token

        and:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_PHONE_NUMBER_NULL
    }

    def 'Should throw an exception while process EMPLOYEE creation cause phone number code is empty'() {
        given:
        def companyName = 'Jobbed'
        def code = '111111'
        def phoneNumberCode = null
        def dto = new CreateUserDto(
                'John', 'Doe', 'john.doe@mail.com', '+48123456789', phoneNumberCode,
                '12345!aA', '12345aA!',
                companyName, code, ROLE_EMPLOYEE)
        def company = new CompanyAggregate()
        def token = new TokenAggregate()
        company.name = companyName
        token.companyName = companyName
        token.code = code
        token.roleType = ROLE_EMPLOYEE

        when:
        employeeCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.findByName(_) >> company
        1 * tokenFacade.findByCode(_) >> token

        and:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_PHONE_NUMBER_CODE_NULL
    }

    def 'Should throw an exception while process EMPLOYEE creation cause company name not match'() {
        given:
        def companyName = 'Jobbed'
        def code = '111111'
        def dto = new CreateUserDto(
                'John', 'Doe', 'john.doe@mail.com', '+48123456789', 'PL',
                '12345!aA', '12345aA!',
                companyName, code, ROLE_EMPLOYEE)
        def company = new CompanyAggregate()
        def token = new TokenAggregate()
        company.name = 'definitelyNotJobbed'

        when:
        employeeCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.findByName(_) >> company
        1 * tokenFacade.findByCode(_) >> token

        and:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.TOKEN_NOT_MATCH_WITH_COMPANY
    }

    def 'Should throw an exception while process EMPLOYEE creation cause token company name not match'() {
        given:
        def companyName = 'Jobbed'
        def code = '111111'
        def dto = new CreateUserDto(
                'John', 'Doe', 'john.doe@mail.com', '+48123456789', 'PL',
                '12345!aA', '12345aA!',
                companyName, code, ROLE_EMPLOYEE)
        def company = new CompanyAggregate()
        def token = new TokenAggregate()
        company.name = companyName
        token.companyName = 'definitelyNotJobbed'

        when:
        employeeCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.findByName(_) >> company
        1 * tokenFacade.findByCode(_) >> token

        and:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.TOKEN_NOT_MATCH_WITH_COMPANY
    }

    def 'Should throw an exception while process EMPLOYEE creation cause token role name not match'() {
        given:
        def companyName = 'Jobbed'
        def code = '111111'
        def dto = new CreateUserDto(
                'John', 'Doe', 'john.doe@mail.com', '+48123456789', 'PL',
                '12345!aA', '12345aA!',
                companyName, code, ROLE_EMPLOYEE)
        def company = new CompanyAggregate()
        def token = new TokenAggregate()
        company.name = companyName
        token.companyName = companyName
        token.roleType = RoleType.ROLE_ADMIN

        when:
        employeeCreator.processCreation(dto)

        then: 'company and token should be created'
        1 * companyFacade.findByName(_) >> company
        1 * tokenFacade.findByCode(_) >> token

        and:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.TOKEN_NOT_MATCH_WITH_COMPANY
    }

    def 'Should throw an exception while process update cause phone number is empty'() {
        given:
        def phoneNumber = null
        def dto = createUpdateUserDto('newName', 'newSurname', phoneNumber)
        def command = UpdateUserCommand.of(1L, 'Jobbed', new UserAggregate(), dto)

        when:
        employeeCreator.processUpdate(command)

        then:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_PHONE_NUMBER_NULL
    }
}
