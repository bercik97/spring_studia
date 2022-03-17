package com.jobbed.api.notification.domain.sender.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SendGridConfig {

    @Value("${app.sendGrid.apiKey:#{null}}")
    private String SEND_GRID_API_KEY;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(SEND_GRID_API_KEY);
    }
}
