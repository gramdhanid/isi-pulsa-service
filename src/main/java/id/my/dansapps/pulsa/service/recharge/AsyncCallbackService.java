package id.my.dansapps.pulsa.service.recharge;

import id.my.dansapps.pulsa.dto.CallBackRequest;
import id.my.dansapps.pulsa.model.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncCallbackService {

    @Autowired
    private RestTemplate restTemplate;

    public void scheduleCallback(String transactionId, String callbackUrl,
                                 String finalStatus, int delaySeconds) {

        CompletableFuture.delayedExecutor(delaySeconds, TimeUnit.SECONDS)
                .execute(() -> sendCallback(transactionId, callbackUrl, finalStatus));
    }

    private void sendCallback(String transactionId, String callbackUrl, String finalStatus) {
        try {
            log.info("Sending mock callback for transaction: {} with status: {}",
                    transactionId, finalStatus);

            CallBackRequest callbackRequest = new CallBackRequest();
            callbackRequest.setStatus(finalStatus);
            callbackRequest.setMessage(getCallbackMessage(finalStatus));
            callbackRequest.setProviderTransactionId("PROV_" + transactionId);
            callbackRequest.setTimestamp(LocalDateTime.now().toString());


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CallBackRequest> entity = new HttpEntity<>(callbackRequest, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    callbackUrl,
                    entity,
                    String.class
            );

            log.info("Mock callback sent successfully. Response: {}", response.getStatusCode());

        } catch (Exception e) {
            log.error("Failed to send mock callback for transaction: {}", transactionId, e);
        }
    }

    private String getCallbackMessage(String status) {
        if (TransactionStatus.SUCCESS.name().equals(status)) {
            return "Pulsa berhasil diisi";
        } else {
            String[] failureReasons = {
                    "Nomor tidak aktif",
                    "Provider maintenance",
                    "Insufficient balance",
                    "Network timeout"
            };
            return failureReasons[new Random().nextInt(failureReasons.length)];
        }
    }
}
