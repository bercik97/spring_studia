package com.jobbed.api.notification.domain;

import com.jobbed.api.notification.domain.command.SendNotificationCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationService service;

    public void send(SendNotificationCommand command) {
        service.send(command);
    }
}
