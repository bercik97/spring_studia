package com.jobbed.api.comment.domain.dto;

import com.jobbed.api.comment.domain.CommentAggregate;

import java.time.ZonedDateTime;

public record CommentDto(long id, ZonedDateTime createdDate, String message, String author) {

    public static CommentDto from(CommentAggregate commentAggregate) {
        return new CommentDto(commentAggregate.getId(), commentAggregate.getCreatedDate(), commentAggregate.getMessage(), commentAggregate.getAuthor());
    }
}
