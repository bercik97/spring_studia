package com.jobbed.api.notification.domain;

import com.jobbed.api.notification.domain.command.SendNotificationCommand;
import com.jobbed.api.notification.domain.sender.NotificationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class NotificationFactory {

    public static NotificationEntity create(SendNotificationCommand command, NotificationResult result) {
        return NotificationEntity.builder()
                .recipient(command.recipient())
                .subject(command.notificationSubject())
                .type(command.notificationType())
                .error(result.getError())
                .success(result.isSuccess())
                .build();
    }
}
