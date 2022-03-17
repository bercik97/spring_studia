package com.jobbed.api.error_status

import com.jobbed.api.shared.error.ErrorStatus
import spock.lang.Specification

class ErrorStatusSpec extends Specification {

    def 'Should check if code is increased one by one'() {
        given:
        def errorStatuses = ErrorStatus.values().toList()
        def counter = 0

        expect:
        errorStatuses.forEach({ errorStatus -> errorStatus.code == ++counter })
    }
}
