package id.my.dansapps.pulsa.service.impl;

import id.my.dansapps.common.exception.CustomException;
import id.my.dansapps.common.exception.ErrorCode;
import id.my.dansapps.notification.service.NotificationService;
import id.my.dansapps.pulsa.dto.CallBackRequest;
import id.my.dansapps.pulsa.dto.CallBackResponse;
import id.my.dansapps.pulsa.model.Pulsa;
import id.my.dansapps.pulsa.model.TransactionStatus;
import id.my.dansapps.pulsa.repository.PulsaRepository;
import id.my.dansapps.pulsa.service.CallbackService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CallbackImpl implements CallbackService {

    private final PulsaRepository pulsaRepository;
    private final NotificationService notificationService;

    public CallbackImpl(PulsaRepository pulsaRepository, NotificationService notificationService) {
        this.pulsaRepository = pulsaRepository;
        this.notificationService = notificationService;
    }

    @Override
    public CallBackResponse handlePulsaCallback(String transactionId, CallBackRequest callbackRequest) {
        try {
            CallBackResponse callbackResponse = new CallBackResponse();
            Pulsa pulsa = pulsaRepository.findByTransactionId(transactionId)
                    .orElseThrow(() -> new CustomException("Transaction Not Found", ErrorCode.GENERIC_FAILURE));
            if (TransactionStatus.SUCCESS.name().equals(callbackRequest.getStatus())) {
                pulsa.setStatus(TransactionStatus.SUCCESS);
                notificationService.sendSmsNotification(
                        pulsa.getPhoneNumber(), pulsa.getAmount(), pulsa.getTransactionId(), TransactionStatus.SUCCESS.name());
            } else if (TransactionStatus.FAILED.name().equals(callbackRequest.getStatus())) {
                pulsa.setStatus(TransactionStatus.FAILED);
                pulsa.setFailureReason(callbackRequest.getMessage());

                // Kirim notifikasi gagal
                notificationService.sendSmsNotification(
                        pulsa.getPhoneNumber(),pulsa.getAmount(), pulsa.getTransactionId(), TransactionStatus.FAILED.name());
            }
            pulsa.setUpdatedAt(LocalDateTime.now());
            pulsaRepository.save(pulsa);
            callbackResponse.setSuccess(true);
            callbackResponse.setMessage("Callback processed successfully");
            return callbackResponse;
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }

    }

}
