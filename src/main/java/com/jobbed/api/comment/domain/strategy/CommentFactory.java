package com.jobbed.api.comment.domain.strategy;

import com.jobbed.api.comment.domain.CommentAggregate;
import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CommentFactory {

    public static CommentAggregate create(CreateCommentCommand command) {
        return CommentAggregate.builder()
                .date(LocalDate.now())
                .message(command.message())
                .author(command.author())
                .type(command.type())
                .relationId(command.relationId())
                .companyName(command.companyName())
                .build();
    }
}
