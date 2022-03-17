package com.jobbed.api.confirmation_token.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationTokenAggregate {

    private long id;
    private ZonedDateTime createdDate;
    private String uuid;
    private long userId;
    private ConfirmationTokenType tokenType;
    private ZonedDateTime confirmedDate;

    public ConfirmationTokenEntity toEntity() {
        ConfirmationTokenEntity entity = ConfirmationTokenEntity.builder()
                .uuid(uuid)
                .userId(userId)
                .tokenType(tokenType)
                .confirmedDate(confirmedDate)
                .build();
        entity.setId(id);
        return entity;
    }
}
