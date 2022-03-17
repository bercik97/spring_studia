package com.jobbed.api.user.domain;

import com.jobbed.api.role.RoleAggregate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregate {

    private long id;
    private ZonedDateTime createdDate;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String phoneNumberCode;
    private String password;
    private String companyName;
    private boolean isEnabled;
    private Set<RoleAggregate> roles = new HashSet<>();

    public UserEntity toEntity() {
        UserEntity entity = UserEntity.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .phoneNumber(phoneNumber)
                .phoneNumberCode(phoneNumberCode)
                .password(password)
                .companyName(companyName)
                .isEnabled(isEnabled)
                .roles(roles.stream().map(RoleAggregate::toEntity).collect(Collectors.toSet()))
                .build();
        entity.setId(id);
        return entity;
    }
}
