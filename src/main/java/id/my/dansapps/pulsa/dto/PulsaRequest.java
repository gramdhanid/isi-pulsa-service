package id.my.dansapps.pulsa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PulsaRequest {

    private String phoneNumber;
    private BigDecimal amount;

}