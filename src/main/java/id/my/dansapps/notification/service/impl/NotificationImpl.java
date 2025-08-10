package id.my.dansapps.notification.service.impl;

import id.my.dansapps.notification.service.NotificationService;
import id.my.dansapps.pulsa.model.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class NotificationImpl implements NotificationService {
    @Override
    public void sendSmsNotification(String phoneNumber, BigDecimal amount, String transactionId, String status) {
        log.info("SMS notification");
        if (status.equals(TransactionStatus.SUCCESS.name())) {
            log.info("Pulsa sebesar {} berhasil di isi ke nomer {} dengan kode transaksi {}", amount, transactionId, phoneNumber);
        } else {
            log.info("Pulsa gagal masuk");
        }
    }

    @Override
    public void sendPushNotification(String userId, String message) {

    }

    @Override
    public void sendEmailNotification(String email, String subject, String message) {

    }
}
