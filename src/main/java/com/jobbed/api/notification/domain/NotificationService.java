package com.jobbed.api.notification.domain;

import com.jobbed.api.notification.domain.command.SendNotificationCommand;
import com.jobbed.api.notification.domain.sender.NotificationResult;
import com.jobbed.api.notification.domain.sender.NotificationSenderService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class NotificationService {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationSenderService notificationSenderService;

    private final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void send(SendNotificationCommand command) {
        executorService.submit(() -> {
            NotificationResult result = notificationSenderService.send(command);
            notificationJpaRepository.save(NotificationFactory.create(command, result));
        });
    }
}
