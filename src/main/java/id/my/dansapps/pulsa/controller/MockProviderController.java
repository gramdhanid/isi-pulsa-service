package id.my.dansapps.pulsa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/mock-provider")
@Slf4j
public class MockProviderController {

    private final Random random = new Random();

    @PostMapping("/recharge")
    public ResponseEntity<Map<String, Object>> mockRecharge(@RequestBody Map<String, Object> request) {

        log.info("Mock Provider: Received recharge request: {}", request);

        String phoneNumber = (String) request.get("phone_number");
        String transactionId = (String) request.get("transaction_id");
        String callbackUrl = (String) request.get("callback_url");

        // Simulate processing delay
        try {
            Thread.sleep(200 + random.nextInt(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String providerTxnId = "MOCKPROV_" + System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("provider_transaction_id", providerTxnId);
        response.put("status", "PENDING");
        response.put("message", "Request received and being processed");

        // Schedule callback in separate thread
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000 + random.nextInt(7000)); // 3-10 seconds delay
                sendMockCallback(transactionId, callbackUrl, phoneNumber);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return ResponseEntity.ok(response);
    }

    private void sendMockCallback(String transactionId, String callbackUrl, String phoneNumber) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Determine callback result
            String status = phoneNumber.endsWith("0000") ? "FAILED" : "SUCCESS";
            String message = status.equals("SUCCESS") ? "Recharge completed" : "Phone number invalid";

            Map<String, Object> callback = Map.of(
                    "status", status,
                    "message", message,
                    "provider_transaction_id", "PROV_" + transactionId,
                    "timestamp", LocalDateTime.now().toString()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(callback, headers);

            restTemplate.postForEntity(callbackUrl, entity, String.class);
            log.info("Mock callback sent to: {} with status: {}", callbackUrl, status);

        } catch (Exception e) {
            log.error("Failed to send mock callback", e);
        }
    }
}
