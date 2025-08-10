package id.my.dansapps.notification.service;

import java.math.BigDecimal;

public interface NotificationService {
    void sendSmsNotification(String phoneNumber, BigDecimal amount, String transactionId, String status);
    void sendPushNotification(String userId, String message);
    void sendEmailNotification(String email, String subject, String message);
}
