package id.my.dansapps.pulsa.dto;

import lombok.Data;

@Data
public class CallBackRequest {
    private String status; // SUCCESS, FAILED
    private String message;
    private String providerTransactionId;
    private String timestamp;
}
