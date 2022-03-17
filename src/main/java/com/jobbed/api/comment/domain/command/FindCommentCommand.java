package com.jobbed.api.comment.domain.command;

import com.jobbed.api.comment.domain.CommentType;

import java.time.LocalDate;

public record FindCommentCommand(long relationId, CommentType type, LocalDate date, String companyName) {
}
