package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.command.FindCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository jpaRepository;

    @Override
    public void save(CommentAggregate comment) {
        jpaRepository.save(comment.toEntity());
    }

    @Override
    public Page<CommentAggregate> findAllByRelationIdAndTypeAndDateAndCompanyName(Pageable pageable, FindCommentCommand command) {
        Page<CommentEntity> comments = jpaRepository.findAllByRelationIdAndTypeAndDateAndCompanyName(
                command.relationId(), command.type(), command.date(), command.companyName(), pageable);
        return new PageImpl<>(comments
                .stream()
                .map(CommentEntity::toAggregate)
                .collect(Collectors.toList()), pageable, comments.getTotalElements());
    }
}
