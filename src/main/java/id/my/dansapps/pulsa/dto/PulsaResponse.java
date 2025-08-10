package id.my.dansapps.pulsa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PulsaResponse {
    private boolean success;
    private String transactionId;
    private String status;
    private String message;
    private String phoneNumber;
    private BigDecimal amount;
}
