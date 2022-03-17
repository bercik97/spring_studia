package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.command.FindCommentCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {

    void save(CommentAggregate comment);

    Page<CommentAggregate> findAllByRelationIdAndTypeAndDateAndCompanyName(Pageable pageable, FindCommentCommand command);
}
