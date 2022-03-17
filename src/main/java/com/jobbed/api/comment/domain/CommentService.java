package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import com.jobbed.api.comment.domain.command.FindCommentCommand;
import com.jobbed.api.comment.domain.dto.CommentDto;
import com.jobbed.api.comment.domain.strategy.CommentStrategyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

record CommentService(CommentRepository repository, CommentValidator validator, CommentStrategyService commentStrategyService) {

    public void create(CreateCommentCommand command) {
        validator.validate(command);
        CommentAggregate comment = commentStrategyService.processCreation(command);
        repository.save(comment);
    }

    public Page<CommentDto> findAllByRelationIdAndTypeAndDateAndCompanyName(Pageable pageable, FindCommentCommand command) {
        Page<CommentAggregate> comments = repository.findAllByRelationIdAndTypeAndDateAndCompanyName(pageable, command);
        return new PageImpl<>(comments
                .stream()
                .map(CommentDto::from)
                .collect(Collectors.toList()), pageable, comments.getTotalElements());
    }
}
