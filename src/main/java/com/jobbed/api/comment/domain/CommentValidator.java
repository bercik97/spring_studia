package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.command.CreateCommentCommand;
import com.jobbed.api.shared.error.ErrorStatus;
import com.jobbed.api.shared.exception.type.ValidationException;
import org.apache.logging.log4j.util.Strings;

class CommentValidator {

    public void validate(CreateCommentCommand command) {
        validateMessage(command.message());
    }

    private void validateMessage(String message) {
        if (Strings.isBlank(message)) {
            throw new ValidationException(ErrorStatus.COMMENT_MESSAGE_NULL);
        }
    }
}
