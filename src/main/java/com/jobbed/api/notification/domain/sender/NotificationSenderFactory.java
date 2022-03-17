package com.jobbed.api.notification.domain.sender;

import com.jobbed.api.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationSenderFactory {

    private final List<NotificationSender> notificationSenders;

    public NotificationSender getStrategyFor(NotificationType notificationType) {
        return notificationSenders.stream()
                .filter(strategy -> strategy.isApplicableFor(notificationType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid notificationType for notification sender"));
    }
}
