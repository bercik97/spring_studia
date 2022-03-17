package com.jobbed.api.comment.domain.strategy

import com.jobbed.api.comment.domain.CommentFixture
import com.jobbed.api.comment.domain.CommentType
import com.jobbed.api.comment.domain.command.CreateCommentCommand
import com.jobbed.api.workplace.domain.WorkplaceAggregate
import com.jobbed.api.workplace.domain.WorkplaceFacade
import com.jobbed.api.workplace.domain.dto.WorkplaceDto
import spock.lang.Specification

class CommentWorkplaceStrategyImplSpec extends Specification implements CommentFixture {

    private final static CommentType TYPE = CommentType.WORKPLACE

    private CommentWorkplaceStrategyImpl workplaceStrategy

    private WorkplaceFacade workplaceFacade = Mock(WorkplaceFacade)

    def setup() {
        workplaceStrategy = new CommentWorkplaceStrategyImpl(workplaceFacade)
    }

    def 'Should process WORKPLACE comment creation'() {
        given:
        def author = 'john.doe@mail.com'
        def companyName = 'Jobbed'
        def message = 'Work is done'
        def dto = createCommentDto(message, TYPE)
        def command = CreateCommentCommand.of(dto, author, companyName)

        when:
        def comment = workplaceStrategy.processCreation(command)

        then: 'workplace should be found by relation id and company name'
        1 * workplaceFacade.findByIdAndCompanyName(command.relationId(), command.companyName()) >> WorkplaceDto.from(new WorkplaceAggregate())

        and: 'comment should be created based on passed command'
        comment != null
        comment.message == command.message()
        comment.author == command.author()
        comment.type == command.type()
        comment.relationId == command.relationId()
    }
}
