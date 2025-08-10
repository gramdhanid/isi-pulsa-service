package id.my.dansapps.pulsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_pulsa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pulsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String phoneNumber;
    private BigDecimal amount;
    private String operator;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String providerTransactionId;
    private String callbackUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String failureReason;
}
