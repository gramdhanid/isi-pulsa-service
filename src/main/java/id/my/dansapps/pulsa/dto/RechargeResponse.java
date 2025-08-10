package id.my.dansapps.pulsa.dto;

import lombok.Data;

@Data
public class RechargeResponse {
    private String providerTransactionId;
    private String status;
    private String message;
}
