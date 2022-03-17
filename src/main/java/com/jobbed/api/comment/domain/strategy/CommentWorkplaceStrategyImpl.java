package com.jobbed.api.comment.domain.strategy;

import com.jobbed.api.comment.domain.CommentAggregate;
import com.jobbed.api.comment.domain.CommentType;
import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import com.jobbed.api.workplace.domain.WorkplaceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CommentWorkplaceStrategyImpl implements CommentStrategy {

    private static final CommentType TYPE = CommentType.WORKPLACE;

    private final WorkplaceFacade workplaceFacade;

    @Override
    public boolean isApplicableFor(CommentType commentType) {
        return commentType == TYPE;
    }

    @Override
    public CommentAggregate processCreation(CreateCommentCommand command) {
        validate(command);

        return CommentFactory.create(command);
    }

    private void validate(CreateCommentCommand command) {
        // check if workplace exists, if not then exception will be thrown
        workplaceFacade.findByIdAndCompanyName(command.relationId(), command.companyName());
    }
}
