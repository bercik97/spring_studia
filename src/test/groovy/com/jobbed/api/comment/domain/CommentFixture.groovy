package com.jobbed.api.comment.domain

import com.jobbed.api.comment.domain.dto.CreateCommentDto

import java.time.LocalDate

trait CommentFixture {

    static CreateCommentDto createCommentDto(def message = 'Work is done',
                                             def type = CommentType.MOCKED,
                                             def workplaceId = 1L) {
        return new CreateCommentDto(message, type, workplaceId)
    }

    static CommentEntity createCommentEntity(def date = LocalDate.now(),
                                             def message = 'Work is done',
                                             def author = 'john.doe@mail.com',
                                             def type = CommentType.MOCKED,
                                             def relationId = 1L,
                                             def companyName = 'Jobbed') {
        return new CommentEntity(date, message, author, type, relationId, companyName)
    }
}
