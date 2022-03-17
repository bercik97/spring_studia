package com.jobbed.api.user.domain.dto;

import com.jobbed.api.role.RoleType;
import com.jobbed.api.shared.converter.StringToLowerCaseConverter;

public record CreateUserDto(String name,
                            String surname,
                            String email,
                            String phoneNumber,
                            String phoneNumberCode,
                            String password,
                            String rePassword,
                            String companyName,
                            String tokenCode,
                            RoleType roleType) {

    public CreateUserDto(String name,
                         String surname,
                         String email,
                         String phoneNumber,
                         String phoneNumberCode,
                         String password,
                         String rePassword,
                         String companyName,
                         String tokenCode,
                         RoleType roleType) {
        this.name = name;
        this.surname = surname;
        this.email = StringToLowerCaseConverter.convert(email);
        this.phoneNumber = phoneNumber;
        this.phoneNumberCode = phoneNumberCode;
        this.password = password;
        this.rePassword = rePassword;
        this.companyName = companyName;
        this.tokenCode = tokenCode;
        this.roleType = roleType;
    }
}
