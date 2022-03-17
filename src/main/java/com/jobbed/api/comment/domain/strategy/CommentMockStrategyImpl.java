package com.jobbed.api.comment.domain.strategy;

import com.jobbed.api.comment.domain.CommentAggregate;
import com.jobbed.api.comment.domain.CommentType;
import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class CommentMockStrategyImpl implements CommentStrategy {

    private static long idCounter = 0;

    @Override
    public boolean isApplicableFor(CommentType commentType) {
        return commentType == CommentType.MOCKED;
    }

    @Override
    public CommentAggregate processCreation(CreateCommentCommand command) {
        return new CommentAggregate(
                ++idCounter,
                ZonedDateTime.now(),
                LocalDate.now(),
                command.message(),
                command.author(),
                null,
                null,
                command.companyName());
    }
}
