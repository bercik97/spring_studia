package com.jobbed.api.notification.domain.sender;

import lombok.Value;

@Value
public class NotificationResult {

    String error;
    boolean success;

    public static NotificationResult success() {
        return new NotificationResult(null, true);
    }

    public static NotificationResult error(String error) {
        return new NotificationResult(error, false);
    }
}
