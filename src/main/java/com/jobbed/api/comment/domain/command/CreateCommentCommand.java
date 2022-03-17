package com.jobbed.api.comment.domain.command;

import com.jobbed.api.comment.domain.CommentType;
import com.jobbed.api.comment.domain.dto.CreateCommentDto;

public record CreateCommentCommand(String message, String author, CommentType type, long relationId, String companyName) {

    public static CreateCommentCommand of(CreateCommentDto dto, String author, String companyName) {
        return new CreateCommentCommand(dto.message(), author, dto.type(), dto.relationId(), companyName);
    }
}
