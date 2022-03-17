package com.jobbed.api.notification.domain.command;

import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.notification.domain.NotificationSubject;
import com.jobbed.api.notification.domain.NotificationType;

public record SendNotificationCommand(String recipient,
                                      ConfirmationTokenUuid confirmationTokenUuid,
                                      NotificationSubject notificationSubject,
                                      NotificationType notificationType) {
}
