package com.jobbed.api.comment.domain.dto;

import com.jobbed.api.comment.domain.CommentType;

public record CreateCommentDto(String message, CommentType type, long relationId) {
}
