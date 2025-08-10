package id.my.dansapps.pulsa.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PulsaCheck {
    private String transactionId;
    private String status;
    private String phoneNumber;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String failureReason;
}
