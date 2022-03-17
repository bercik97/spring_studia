package com.jobbed.api.comment.domain.strategy;

import com.jobbed.api.comment.domain.CommentAggregate;
import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentStrategyService {

    private final CommentStrategyFactory commentStrategyFactory;

    public CommentAggregate processCreation(CreateCommentCommand command) {
        CommentStrategy strategy = commentStrategyFactory.getStrategyFor(command.type());
        return strategy.processCreation(command);
    }
}
