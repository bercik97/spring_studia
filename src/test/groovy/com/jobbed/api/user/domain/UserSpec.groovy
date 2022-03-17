package com.jobbed.api.user.domain

import com.jobbed.api.confirmation_token.domain.ConfirmationTokenFacade
import com.jobbed.api.notification.domain.NotificationFacade
import com.jobbed.api.role.RoleType
import com.jobbed.api.shared.error.ErrorStatus
import com.jobbed.api.shared.exception.type.NotFoundException
import com.jobbed.api.shared.exception.type.ValidationException
import com.jobbed.api.user.domain.dto.CreateUserDto
import com.jobbed.api.user.domain.strategy.MockStrategyImpl
import com.jobbed.api.user.domain.strategy.UserStrategy
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.ConcurrentHashMap

class UserSpec extends Specification implements UserFixture {

    private UserFacade userFacade

    private List<UserStrategy> userCreatorStrategies = List.of(new MockStrategyImpl())

    private ConfirmationTokenFacade confirmationTokenFacade = Mock(ConfirmationTokenFacade)

    private NotificationFacade notificationFacade = Mock(NotificationFacade)

    private PasswordEncoder passwordEncoder = Mock(PasswordEncoder)

    private ConcurrentHashMap<Long, UserEntity> db

    def setup() {
        db = new ConcurrentHashMap<>()
        userFacade = new UserConfiguration().userFacade(db, userCreatorStrategies, confirmationTokenFacade, notificationFacade, passwordEncoder)
    }

    def 'Should create user'() {
        given:
        def dto = createUserDto()

        when:
        userFacade.create(dto)

        then:
        1 * notificationFacade.send(_)
        !db.isEmpty()
    }

    @Unroll
    def 'Should throw an exception cause email format = [#email]'(String email) {
        given:
        def dto = createUserDto(email)

        when: 'we try to create an user'
        userFacade.create(dto)

        then: 'exception is thrown'
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_EMAIL_WRONG_FORMAT

        where:
        email                          | _
        'plainaddress'                 | _
        '#@%^%#$@#$@#.com'             | _
        '@domain.com'                  | _
        'Joe Smith <email@domain.com>' | _
        'email.domain.com'             | _
        'email@domain@domain.com'      | _
        '.email@domain.com'            | _
        'email.@domain.com'            | _
        'email..email@domain.com'      | _
        'あいうえお@domain.com'             | _
        'email@domain.com (Joe Smith)' | _
        'email@domain'                 | _
    }

    @Unroll
    def 'Should throw an exception cause password is not safe = [#password]'(String password) {
        given:
        def dto = createUserDto('john.doe@mail.com', password)

        when: 'we try to create user'
        userFacade.create(dto)

        then: 'exception is thrown'
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_PASSWORD_NOT_SAFE

        where:
        password     | _
        '1234'       | _
        '12345678'   | _
        'qwerty12'   | _
        'qwerty1!'   | _
        'qwerty1YY1' | _
    }

    @Unroll
    def 'Should throw an exception cause some of fields are invalid'(expectedErrorStatus,
                                                                     name,
                                                                     surname,
                                                                     email,
                                                                     password,
                                                                     rePassword,
                                                                     roleType) {
        given:
        def dto = new CreateUserDto(
                name,
                surname,
                email,
                '+48123456789',
                'PL',
                password,
                rePassword,
                'Jobbed',
                '111111',
                roleType)

        when: 'we try to create an user'
        userFacade.create(dto)

        then: 'exception is thrown'
        ValidationException exception = thrown()
        exception.errorStatus == expectedErrorStatus

        where:
        expectedErrorStatus               | name   | surname | email               | password   | rePassword | roleType               | _
        ErrorStatus.USER_NAME_NULL        | null   | 'Doe'   | 'john.doe@mail.com' | '12345Aa!' | '12345Aa!' | RoleType.ROLE_DIRECTOR | _
        ErrorStatus.USER_SURNAME_NULL     | 'John' | null    | 'john.doe@mail.com' | '12345Aa!' | '12345Aa!' | RoleType.ROLE_DIRECTOR | _
        ErrorStatus.USER_EMAIL_NULL       | 'John' | 'Doe'   | null                | '12345Aa!' | '12345Aa!' | RoleType.ROLE_DIRECTOR | _
        ErrorStatus.USER_PASSWORD_NULL    | 'John' | 'Doe'   | 'john.doe@mail.com' | null       | '12345Aa!' | RoleType.ROLE_DIRECTOR | _
        ErrorStatus.USER_RE_PASSWORD_NULL | 'John' | 'Doe'   | 'john.doe@mail.com' | '12345Aa!' | null       | RoleType.ROLE_DIRECTOR | _
        ErrorStatus.USER_ROLE_NULL        | 'John' | 'Doe'   | 'john.doe@mail.com' | '12345Aa!' | '12345Aa!' | null                   | _
    }

    def 'Should update password'() {
        given:
        def command = createUpdatePasswordCommand()
        def user = createUserEntity()
        db.put(1L, user)

        when: 'we try to update password'
        userFacade.updatePassword(command)

        then: 'password is updated'
        passwordEncoder.matches(command.oldPassword(), command.currentPassword()) >> true
        user.password == command.newPassword()
    }

    def 'Should throw an exception cause old password do not match current password'() {
        given:
        def currentPassword = '12345!aA'
        def oldPassword = '12345!aB'
        def command = createUpdatePasswordCommand('john.doe@mail.com', currentPassword, oldPassword)
        def user = createUserEntity()
        db.put(1L, user)

        when: 'we try to update password'
        userFacade.updatePassword(command)

        then: 'exception is thrown'
        passwordEncoder.matches(command.oldPassword(), command.currentPassword()) >> false
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_OLD_PASSWORD_DO_NOT_MATCH_WITH_CURRENT_PASSWORD

        and:
        currentPassword != oldPassword
    }

    def 'Should find all users by company name except user id which is passed'() {
        given:
        def companyName = 'Jobbed'
        userFacade.create(new CreateUserDto(
                'John', 'Doe',
                'john.doe1@mail.com', null, null,
                '12345!aA', '12345!aA', companyName,
                '111111', RoleType.ROLE_EMPLOYEE))
        userFacade.create(new CreateUserDto(
                'John', 'Doe',
                'john.doe2@mail.com', null, null,
                '12345!aA', '12345!aA', companyName,
                '111111', RoleType.ROLE_EMPLOYEE))
        def user = db.values().stream().findFirst().get()

        when:
        def foundUsers = userFacade.findAllByCompanyNameExceptMe(Pageable.unpaged(), companyName, user.id)

        then: 'found users do not contains user with id 1L'
        foundUsers.size == 1
        !foundUsers.stream().filter({ u -> u.id() == user.id }).isParallel()
    }

    def 'Should find user by id and company name'() {
        given:
        def companyName = 'Jobbed'
        userFacade.create(new CreateUserDto(
                'John', 'Doe',
                'john.doe1@mail.com', null, null,
                '12345!aA', '12345!aA', companyName,
                '111111', RoleType.ROLE_EMPLOYEE))
        def user = db.values().stream().findFirst().get()

        when:
        def foundUser = userFacade.findByIdAndCompanyName(user.id, companyName)

        then:
        foundUser != null
    }

    def 'Should find user by email and company name'() {
        given:
        def companyName = 'Jobbed'
        userFacade.create(new CreateUserDto(
                'John', 'Doe',
                'john.doe1@mail.com', null, null,
                '12345!aA', '12345!aA', companyName,
                '111111', RoleType.ROLE_EMPLOYEE))
        def user = db.values().stream().findFirst().get()

        when:
        def foundUser = userFacade.findByEmailAndCompanyName(user.email, companyName)

        then:
        foundUser != null
    }

    def 'Should throw an exception while trying to find user by non existing id or company name'() {
        when: 'we try to find user by non existing id and non existing company name'
        userFacade.findByIdAndCompanyName(999L, 'nonExistingCompanyName')

        then: 'exception is thrown'
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_NOT_FOUND
    }

    def 'Should delete user by id and company name'() {
        given:
        def companyName = 'Jobbed'
        userFacade.create(new CreateUserDto(
                'John', 'Doe',
                'john.doe1@mail.com', null, null,
                '12345!aA', '12345!aA', companyName,
                '111111', RoleType.ROLE_EMPLOYEE))
        def user = db.values().stream().findFirst().get()

        when:
        userFacade.deleteByIdAndCompanyName(user.id, companyName)

        then:
        db.isEmpty()
    }

    def 'Should throw an exception while trying to delete user by non existing id or company name'() {
        when: 'we try to delete user by non existing id and non existing company name'
        userFacade.deleteByIdAndCompanyName(999L, 'nonExistingCompanyName')

        then: 'exception is thrown'
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_NOT_FOUND
    }

    def 'Should update user'() {
        given:
        def user = createUserEntity()
        db.put(1L, user)

        and:
        def newName = 'newName'
        def newSurname = 'newSurname'
        def newPhoneNumber = 'newPhoneNumber'
        def dto = createUpdateUserDto(newName, newSurname, newPhoneNumber)

        when: 'we try to update name, surname and phoneNumber'
        userFacade.updateByIdAndCompanyName(user.id, user.companyName, dto)

        then: 'values are updated'
        def updatedUser = userFacade.findByIdAndCompanyName(user.id, user.companyName)
        updatedUser.name() == newName
        updatedUser.surname() == newSurname
        updatedUser.phoneNumber() == newPhoneNumber
    }

    def 'Should throw an exception cause user do not exists'() {
        when:
        userFacade.updateByIdAndCompanyName(999L, 'nonExistingCompanyName', createUpdateUserDto())

        then:
        NotFoundException exception = thrown()
        exception.errorStatus == ErrorStatus.USER_NOT_FOUND
    }
}
