package com.jobbed.api.role.domain

import com.jobbed.api.role.RoleType
import spock.lang.Specification

class RoleSpec extends Specification {

    def 'Should create role aggregate with specific id by given role name = #role'(def role, def expectedRoleId, def expectedRole) {
        when:
        def createdRoleAggregate = RoleType.valueOf(role).create()

        then:
        createdRoleAggregate.id == expectedRoleId && createdRoleAggregate.role == expectedRole

        where:
        role            | expectedRoleId | expectedRole    | _
        'ROLE_DIRECTOR' | 1L             | 'ROLE_DIRECTOR' | _
        'ROLE_EMPLOYEE' | 2L             | 'ROLE_EMPLOYEE' | _
        'ROLE_ADMIN'    | 3L             | 'ROLE_ADMIN'    | _
    }
}
