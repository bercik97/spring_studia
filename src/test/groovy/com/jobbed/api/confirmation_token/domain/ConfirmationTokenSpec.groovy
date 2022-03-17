package com.jobbed.api.confirmation_token.domain

import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class ConfirmationTokenSpec extends Specification implements ConfirmationTokenFixture {

    private ConfirmationTokenFacade confirmationTokenFacade

    private ConcurrentHashMap<Long, ConfirmationTokenEntity> db

    def setup() {
        db = new ConcurrentHashMap<>()
        confirmationTokenFacade = new ConfirmationTokenConfiguration().confirmationTokenFacade(db)
    }

    def 'Should create confirmation token and return token uuid'() {
        given:
        def command = createConfirmationTokenCommand()

        when:
        def uuid = confirmationTokenFacade.create(command)

        then:
        !db.isEmpty() && uuid != null
    }

    def 'Should confirm token by setting confirmation date to current time'() {
        given:
        def confirmationToken = createConfirmationTokenEntity()
        def confirmationTokenId = 1L
        db.put(confirmationTokenId, confirmationToken)

        when:
        def confirmationTokenVO = confirmationTokenFacade.confirm(new ConfirmationTokenUuid(confirmationToken.uuid))

        then:
        db.get(confirmationTokenId).uuid == confirmationTokenVO.confirmationTokenUuid().uuid()
        db.get(confirmationTokenId).confirmedDate != null
    }

    def 'Should do not confirm token by setting confirmation date cause cannot find token by uuid'() {
        when:
        def confirmationTokenVO = confirmationTokenFacade.confirm(new ConfirmationTokenUuid('wrongUuid'))

        then:
        db.isEmpty() && confirmationTokenVO == null
    }

    def 'Should do not confirm token for the second time cause its already confirmed'() {
        given:
        def confirmationToken = createConfirmationTokenEntity()
        def confirmationTokenId = 1L
        db.put(confirmationTokenId, confirmationToken)

        when:
        def confirmationTokenVOStTime = confirmationTokenFacade.confirm(new ConfirmationTokenUuid(confirmationToken.uuid))

        and:
        def confirmationTokenVONdTime = confirmationTokenFacade.confirm(new ConfirmationTokenUuid(confirmationToken.uuid))

        then:
        db.get(confirmationTokenId).uuid == confirmationTokenVOStTime.confirmationTokenUuid().uuid()
        db.get(confirmationTokenId).confirmedDate != null

        and: 'for the second time confirmation is null'
        confirmationTokenVONdTime == null
    }
}
