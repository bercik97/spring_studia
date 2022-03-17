package com.jobbed.api.confirmation_token.domain;

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
import java.time.ZonedDateTime;

@Entity
@Table(name = "confirmation_token", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class ConfirmationTokenEntity extends BaseEntity {

    private String uuid;
    private long userId;
    @Enumerated(EnumType.STRING)
    private ConfirmationTokenType tokenType;
    private ZonedDateTime confirmedDate;

    public ConfirmationTokenAggregate toAggregate() {
        return ConfirmationTokenAggregate.builder()
                .id(getId())
                .createdDate(getCreatedDate())
                .uuid(uuid)
                .userId(userId)
                .tokenType(tokenType)
                .confirmedDate(confirmedDate)
                .build();
    }
}
