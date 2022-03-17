package com.jobbed.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class JobbedAPISpec extends Specification {

    @Autowired
    private ApplicationContext context

    def 'Should load Spring context'() {
        expect:
        context != null
    }
}
