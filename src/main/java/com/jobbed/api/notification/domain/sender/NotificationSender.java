package com.jobbed.api.notification.domain.sender;

import com.jobbed.api.notification.domain.NotificationType;
import com.jobbed.api.notification.domain.command.SendNotificationCommand;

public interface NotificationSender {

    boolean isApplicableFor(NotificationType type);

    NotificationResult send(SendNotificationCommand command);
}
