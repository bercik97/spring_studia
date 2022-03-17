package com.jobbed.api.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAggregate {

    private long id;
    private ZonedDateTime createdDate;
    private LocalDate date;
    private String message;
    private String author;
    private CommentType type;
    private Long relationId;
    private String companyName;

    public CommentEntity toEntity() {
        CommentEntity entity = CommentEntity.builder()
                .date(date)
                .message(message)
                .author(author)
                .type(type)
                .relationId(relationId)
                .companyName(companyName)
                .build();
        entity.setId(id);
        return entity;
    }
}
