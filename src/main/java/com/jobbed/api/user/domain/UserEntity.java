package com.jobbed.api.user.domain;

import com.jobbed.api.role.RoleEntity;
import com.jobbed.api.shared.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class UserEntity extends BaseEntity {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String phoneNumberCode;
    private String password;
    private String companyName;
    private boolean isEnabled;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles = new HashSet<>();

    public UserAggregate toAggregate() {
        return UserAggregate.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .name(name)
                .surname(surname)
                .email(email)
                .phoneNumber(phoneNumber)
                .phoneNumberCode(phoneNumberCode)
                .password(password)
                .companyName(companyName)
                .isEnabled(isEnabled)
                .roles(roles.stream().map(RoleEntity::toAggregate).collect(Collectors.toSet()))
                .build();
    }
}
