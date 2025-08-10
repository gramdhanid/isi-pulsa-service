package id.my.dansapps.pulsa.service.recharge;

import id.my.dansapps.pulsa.dto.RechargeRequest;
import id.my.dansapps.pulsa.dto.RechargeResponse;
import id.my.dansapps.pulsa.model.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Slf4j
public class RechargeImpl implements RechargeService {

    @Autowired
    private AsyncCallbackService asyncCallbackService;

    private final Random random = new Random();

    @Override
    public RechargeResponse requestRecharge(RechargeRequest request) {
        log.info("Simulasi: Processing isi pulsa untuk nomor {} dengan jumlah {}",
                request.getPhoneNumber(), request.getAmount());

        try {
            Thread.sleep(500 + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String providerTxnId = "MOCK_" + System.currentTimeMillis() +
                String.format("%03d", random.nextInt(999));
        RechargeResponse response = simulasiResponseProvider(request, providerTxnId);
        scheduleAsyncCallback(request, response);

        return response;
    }

    private RechargeResponse simulasiResponseProvider(RechargeRequest request, String providerTxnId) {
        String phoneNumber = request.getPhoneNumber();
        BigDecimal amount = request.getAmount();

        if (phoneNumber.endsWith("0000")) {
            return toResponse(TransactionStatus.REJECTED.name(), "Invalid phone number", providerTxnId);
        }

        return toResponse(TransactionStatus.PENDING.name(), "Request accepted, processing", providerTxnId);
    }

    private RechargeResponse toResponse (String status, String message, String providerTxnId) {
        RechargeResponse response = new RechargeResponse();
        response.setProviderTransactionId(providerTxnId);
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

    private void scheduleAsyncCallback(RechargeRequest request, RechargeResponse response) {
        if (TransactionStatus.PENDING.name().equals(response.getStatus())) {
            int delaySeconds = 5 + random.nextInt(10);

            asyncCallbackService.scheduleCallback(
                    request.getTransactionId(),
                    request.getCallbackUrl(),
                    determineCallbackStatus(request),
                    delaySeconds
            );
        } else if (TransactionStatus.REJECTED.name().equals(response.getStatus())) {
            int delaySeconds = 5 + random.nextInt(10);

            asyncCallbackService.scheduleCallback(
                    request.getTransactionId(),
                    request.getCallbackUrl(),
                    determineCallbackStatus(request),
                    delaySeconds
            );
        }
    }

    private String determineCallbackStatus(RechargeRequest request) {
        String phoneNumber = request.getPhoneNumber();
        if (phoneNumber.endsWith("0000")) {
            return TransactionStatus.FAILED.name();
        }
        return TransactionStatus.SUCCESS.name();
    }
}
