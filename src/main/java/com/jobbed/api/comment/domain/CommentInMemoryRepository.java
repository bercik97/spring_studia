package com.jobbed.api.comment.domain;

import com.jobbed.api.comment.domain.command.FindCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CommentInMemoryRepository implements CommentRepository {

    private final ConcurrentHashMap<Long, CommentEntity> db;

    private static long idCounter = 0;

    @Override
    public void save(CommentAggregate comment) {
        CommentEntity entity = comment.toEntity();
        entity.setId(++idCounter);
        db.put(idCounter, entity);
    }

    @Override
    public Page<CommentAggregate> findAllByRelationIdAndTypeAndDateAndCompanyName(Pageable pageable, FindCommentCommand command) {
        return new PageImpl<>(db.values()
                .stream()
                .filter(comment -> comment.getRelationId() == command.relationId())
                .filter(comment -> comment.getType() == command.type())
                .filter(comment -> comment.getDate().isEqual(command.date()))
                .filter(comment -> comment.getCompanyName().equals(command.companyName()))
                .map(CommentEntity::toAggregate)
                .collect(Collectors.toList()), pageable, db.values().size());
    }
}
