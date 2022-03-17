package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import com.jobbed.api.comment.domain.command.FindCommentCommand;
import com.jobbed.api.comment.domain.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService service;

    public void create(CreateCommentCommand command) {
        service.create(command);
    }

    public Page<CommentDto> findAllByRelationIdAndTypeAndDateAndCompanyName(Pageable pageable, FindCommentCommand command) {
        return service.findAllByRelationIdAndTypeAndDateAndCompanyName(pageable, command);
    }
}
