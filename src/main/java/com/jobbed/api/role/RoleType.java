package com.jobbed.api.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_DIRECTOR(1L, "ROLE_DIRECTOR", "DIRECTOR") {
        @Override
        public RoleAggregate create() {
            return new RoleAggregate(1L, "ROLE_DIRECTOR");
        }
    },
    ROLE_EMPLOYEE(2L, "ROLE_EMPLOYEE", "EMPLOYEE") {
        @Override
        public RoleAggregate create() {
            return new RoleAggregate(2L, "ROLE_EMPLOYEE");
        }
    },
    ROLE_ADMIN(3L, "ROLE_ADMIN", "ADMIN") {
        @Override
        public RoleAggregate create() {
            return new RoleAggregate(3L, "ROLE_ADMIN");
        }
    };

    private final long id;
    private final String name;
    private final String nameWithoutPrefix;

    public abstract RoleAggregate create();
}
