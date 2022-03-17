package com.jobbed.api.notification.domain;

import com.jobbed.api.notification.domain.sender.NotificationSender;
import com.jobbed.api.notification.domain.sender.NotificationSenderFactory;
import com.jobbed.api.notification.domain.sender.NotificationSenderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class NotificationConfiguration {

    @Bean
    public NotificationFacade notificationFacade(NotificationJpaRepository jpaRepository,
                                                 List<NotificationSender> notificationSenders) {
        NotificationSenderFactory notificationSenderFactory = new NotificationSenderFactory(notificationSenders);
        NotificationSenderService notificationSenderService = new NotificationSenderService(notificationSenderFactory);
        NotificationService service = new NotificationService(jpaRepository, notificationSenderService);
        return new NotificationFacade(service);
    }
}
