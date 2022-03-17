package com.jobbed.api.user.domain

import com.jobbed.api.role.RoleType
import com.jobbed.api.user.domain.command.UpdatePasswordCommand
import com.jobbed.api.user.domain.dto.CreateUserDto
import com.jobbed.api.user.domain.dto.UpdateUserDto

trait UserFixture {

    static UserEntity createUserEntity() {
        return new UserEntity(
                'John',
                'Doe',
                'john.doe@mail.com',
                '+48123456789',
                'PL',
                '12345aA!',
                'Jobbed',
                true,
                Set.of(RoleType.ROLE_DIRECTOR.create().toEntity()))
    }

    static CreateUserDto createUserDto(def email = 'john.doe@mail.com', def password = '12345aA!', def roleType = RoleType.ROLE_DIRECTOR) {
        return new CreateUserDto('John',
                'Doe',
                email,
                '+48123456789',
                'PL',
                password,
                '12345aA!',
                'Jobbed',
                '111111',
                roleType)
    }

    static UpdateUserDto createUpdateUserDto(def name = 'newName', def surname = 'newSurname', def phoneNumber = 'newPhoneNumber', def phoneNumberCode = 'PL') {
        return new UpdateUserDto(name, surname, phoneNumber, phoneNumberCode)
    }

    static UpdatePasswordCommand createUpdatePasswordCommand(def email = 'john.doe@mail.com',
                                                             def currentPassword = '12345!aA',
                                                             def oldPassword = '12345!aA',
                                                             def newPassword = '12345!aB',
                                                             def reNewPassword = '12345!aB') {
        return new UpdatePasswordCommand(email, currentPassword, oldPassword, newPassword, reNewPassword)
    }
}
