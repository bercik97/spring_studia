package com.jobbed.api.comment.domain

import com.jobbed.api.comment.domain.command.CreateCommentCommand
import com.jobbed.api.comment.domain.command.FindCommentCommand
import com.jobbed.api.comment.domain.strategy.CommentMockStrategyImpl
import com.jobbed.api.comment.domain.strategy.CommentStrategy
import com.jobbed.api.shared.error.ErrorStatus
import com.jobbed.api.shared.exception.type.ValidationException
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

class CommentSpec extends Specification implements CommentFixture {

    private CommentFacade commentFacade

    private ConcurrentHashMap<Long, CommentEntity> db

    private List<CommentStrategy> commentCreatorStrategies = List.of(new CommentMockStrategyImpl())

    def setup() {
        db = new ConcurrentHashMap<>()
        commentFacade = new CommentConfiguration().commentFacade(db, commentCreatorStrategies)
    }

    def 'Should create comment'() {
        given:
        def author = 'john.doe@mail.com'
        def companyName = 'Jobbed'
        def dto = createCommentDto()
        def command = CreateCommentCommand.of(dto, author, companyName)

        when:
        commentFacade.create(command)

        then:
        !db.isEmpty()
    }

    def 'Should throw an exception when try to create comment without message'() {
        given:
        def author = 'john.doe@mail.com'
        def companyName = 'Jobbed'
        def message = null
        def dto = createCommentDto(message)
        def command = CreateCommentCommand.of(dto, author, companyName)

        when:
        commentFacade.create(command)

        then:
        ValidationException exception = thrown()
        exception.errorStatus == ErrorStatus.COMMENT_MESSAGE_NULL
    }

    def 'Should find all comments by relation id, type, date and company name'() {
        given:
        def relationId = 1L
        def type = CommentType.MOCKED
        def date = LocalDate.now()
        def companyName = 'Jobbed'
        def comment = createCommentEntity(date, null, null, type, relationId, companyName)
        def command = new FindCommentCommand(comment.relationId, comment.type, comment.date, comment.companyName)
        db.put(1L, comment)

        when:
        def foundComments = commentFacade.findAllByRelationIdAndTypeAndDateAndCompanyName(Pageable.unpaged(), command)

        then:
        !foundComments.isEmpty()
    }
}
