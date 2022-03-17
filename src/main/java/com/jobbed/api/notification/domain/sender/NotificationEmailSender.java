package com.jobbed.api.notification.domain.sender;

import com.jobbed.api.confirmation_token.domain.vo.ConfirmationTokenUuid;
import com.jobbed.api.notification.domain.NotificationType;
import com.jobbed.api.notification.domain.command.SendNotificationCommand;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile(value = {"beta", "prod"})
class NotificationEmailSender implements NotificationSender {

    private final SendGrid sendGrid;

    @Value("${app.url:#{'http://localhost:8080'}}")
    private String APP_URL;
    @Value("${app.mail:#{''}}")
    private String APP_MAIL;
    @Value("${app.sendGrid.template.confirmationAccountId:#{''}}")
    private String SEND_GRID_TEMPLATE_CONFIRMATION_ACCOUNT_ID;

    private static final NotificationType NOTIFICATION_EMAIL_TYPE = NotificationType.EMAIL;
    private static final String VERIFICATION_URL = "verificationUrl";

    @Override
    public boolean isApplicableFor(NotificationType type) {
        return NOTIFICATION_EMAIL_TYPE == type;
    }

    @Override
    public NotificationResult send(SendNotificationCommand command) {
        try {
            Request request = prepareRequest(command);
            Response response = sendGrid.api(request);

            log.info("SendGrid API response: statusCode {} | body: {} | headers: {}", response.getStatusCode(), response.getBody(), response.getHeaders());

            boolean isStatusCodeIs2XX = String.valueOf(response.getStatusCode()).charAt(0) == '2';

            return isStatusCodeIs2XX ? NotificationResult.success() : NotificationResult.error(response.getBody());
        } catch (IOException e) {
            return NotificationResult.error(e.getMessage());
        }
    }

    private Request prepareRequest(SendNotificationCommand command) throws IOException {
        Email from = new Email(APP_MAIL);
        Email to = new Email(command.recipient());

        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.addDynamicTemplateData(VERIFICATION_URL, buildUrl(command.confirmationTokenUuid()));

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.addPersonalization(personalization);
        mail.setTemplateId(SEND_GRID_TEMPLATE_CONFIRMATION_ACCOUNT_ID);

        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }

    private String buildUrl(ConfirmationTokenUuid confirmationTokenUuid) {
        return APP_URL + "/account-confirmation?token=" + confirmationTokenUuid.uuid();
    }
}
