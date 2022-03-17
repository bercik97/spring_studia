package com.jobbed.api.comment.domain.strategy;

import com.jobbed.api.comment.domain.CommentAggregate;
import com.jobbed.api.comment.domain.CommentType;
import com.jobbed.api.comment.domain.command.CreateCommentCommand;

public interface CommentStrategy {

    boolean isApplicableFor(CommentType commentType);

    CommentAggregate processCreation(CreateCommentCommand command);
}
