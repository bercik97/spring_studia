package com.jobbed.api.notification.domain.sender;

import com.jobbed.api.notification.domain.command.SendNotificationCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationSenderService {

    private final NotificationSenderFactory notificationSenderFactory;

    public NotificationResult send(SendNotificationCommand command) {
        NotificationSender sender = notificationSenderFactory.getStrategyFor(command.notificationType());
        return sender.send(command);
    }
}
