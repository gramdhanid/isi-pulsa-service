package id.my.dansapps.pulsa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeRequest {
    private String phoneNumber;
    private BigDecimal amount;
    private String transactionId;
    private String callbackUrl;
}
