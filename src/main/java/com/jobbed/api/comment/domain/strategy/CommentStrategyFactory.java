package com.jobbed.api.comment.domain.strategy;

import com.jobbed.api.comment.domain.CommentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentStrategyFactory {

    private final List<CommentStrategy> commentStrategies;

    public CommentStrategy getStrategyFor(CommentType commentType) {
        return commentStrategies.stream()
                .filter(strategy -> strategy.isApplicableFor(commentType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid commentType for comment strategy"));
    }
}
