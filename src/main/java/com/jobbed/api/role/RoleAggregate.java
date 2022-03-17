package com.jobbed.api.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleAggregate {

    private long id;
    private String role;

    public RoleEntity toEntity() {
        return new RoleEntity(id, role);
    }
}
