package com.jobbed.api.comment.domain;

import com.jobbed.api.shared.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "comment", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class CommentEntity extends BaseEntity {

    // todo we should date field and filter sql queries by created date
    private LocalDate date;
    private String message;
    private String author;
    @Enumerated(EnumType.STRING)
    private CommentType type;
    private Long relationId;
    private String companyName;

    public CommentAggregate toAggregate() {
        return CommentAggregate.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .date(date)
                .message(message)
                .author(author)
                .type(type)
                .relationId(relationId)
                .companyName(companyName)
                .build();
    }
}
