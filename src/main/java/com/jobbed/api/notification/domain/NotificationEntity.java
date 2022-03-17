package com.jobbed.api.notification.domain;

import com.jobbed.api.shared.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "notification", schema = "public")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class NotificationEntity extends BaseEntity {

    private String recipient;
    @Enumerated(EnumType.STRING)
    private NotificationSubject subject;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String error;
    private boolean success;
}
