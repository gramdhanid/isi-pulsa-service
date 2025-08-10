package id.my.dansapps.pulsa.service.impl;

import id.my.dansapps.common.exception.CustomException;
import id.my.dansapps.common.exception.ErrorCode;
import id.my.dansapps.common.util.messages.ErrorMessages;
import id.my.dansapps.notification.service.NotificationService;
import id.my.dansapps.pulsa.dto.*;
import id.my.dansapps.pulsa.model.Pulsa;
import id.my.dansapps.pulsa.model.TransactionStatus;
import id.my.dansapps.pulsa.repository.PulsaRepository;
import id.my.dansapps.pulsa.service.PulsaService;
import id.my.dansapps.pulsa.service.recharge.RechargeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class PulsaImpl implements PulsaService {

    private final PulsaRepository pulsaRepository;
    private final RechargeService rechargeService;
    private final NotificationService notificationService;

    public PulsaImpl(PulsaRepository pulsaRepository, RechargeService rechargeService, NotificationService notificationService) {
        this.pulsaRepository = pulsaRepository;
        this.rechargeService = rechargeService;
        this.notificationService = notificationService;
    }

    @Override
    public PulsaResponse beliPulsa(PulsaRequest request) {
        String transactionId = generateTransactionId();

        Pulsa pulsa = new Pulsa();
        pulsa.setTransactionId(transactionId);
        pulsa.setPhoneNumber(request.getPhoneNumber());
        pulsa.setAmount(request.getAmount());
        pulsa.setOperator(detectOperator(request.getPhoneNumber()));
        pulsa.setStatus(TransactionStatus.PENDING);
        pulsa.setCallbackUrl(buildCallbackUrl(transactionId));
        pulsa.setCreatedAt(LocalDateTime.now());

        pulsa = pulsaRepository.save(pulsa);

        return requestIsiPulsa(request, transactionId, pulsa);
    }

    private PulsaResponse requestIsiPulsa(PulsaRequest request, String transactionId, Pulsa pulsa) {
        try {
            RechargeRequest rechargeRequest = new RechargeRequest();
            rechargeRequest.setPhoneNumber(request.getPhoneNumber());
            rechargeRequest.setAmount(request.getAmount());
            rechargeRequest.setTransactionId(transactionId);
            rechargeRequest.setCallbackUrl(pulsa.getCallbackUrl());
            RechargeResponse response = rechargeService.requestRecharge(rechargeRequest);
            pulsa.setProviderTransactionId(response.getProviderTransactionId());
            pulsaRepository.save(pulsa);

            return toDTO(pulsa);

        } catch (Exception e) {
            pulsa.setStatus(TransactionStatus.FAILED);
            pulsa.setFailureReason(e.getMessage());
            pulsaRepository.save(pulsa);
            throw new CustomException("Failed to process recharge: " + e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public String detectOperator(String phoneNumber) {
        if (phoneNumber.startsWith("0822") || phoneNumber.startsWith("0812") || phoneNumber.startsWith("0821")) {
            return "TELKOMSEL";
        } else if (phoneNumber.startsWith("08777") || phoneNumber.startsWith("0859")) {
            return "XL";
        } else if (phoneNumber.startsWith("0899") || phoneNumber.startsWith("0896")) {
            return "3";
        }
        return "UNKNOWN";
    }

    @Override
    public String buildCallbackUrl(String transactionId) {
        return "http://localhost:8099/callback/pulsa/" + transactionId;
    }

    @Override
    public String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() +
                String.format("%04d", new Random().nextInt(9999));
    }

    @Override
    public PulsaCheck checkStatus(String transactionId) {
        Optional<Pulsa> transaction = pulsaRepository.findByTransactionId(transactionId);
        if (transaction.isEmpty()) {
            throw new CustomException(ErrorMessages.TRANSACTION_NOT_FOUND, ErrorCode.GENERIC_FAILURE);
        }
        Pulsa txn = transaction.get();
        return toPulsaCHeckDTO(txn);
    }

    public PulsaCheck toPulsaCHeckDTO (Pulsa pulsa) {
        PulsaCheck check = new PulsaCheck();
        check.setTransactionId(pulsa.getTransactionId());
        check.setStatus(pulsa.getStatus().name());
        check.setPhoneNumber(pulsa.getPhoneNumber());
        check.setAmount(pulsa.getAmount());
        check.setCreatedAt(pulsa.getCreatedAt());
        check.setUpdatedAt(pulsa.getUpdatedAt());
        check.setFailureReason(pulsa.getFailureReason());
        return check;
    }

    public PulsaResponse toDTO (Pulsa pulsa) {
        PulsaResponse dto = new PulsaResponse();
        dto.setSuccess(true);
        dto.setTransactionId(pulsa.getTransactionId());
        dto.setStatus(pulsa.getStatus().name());
        dto.setMessage("Recharge request submitted successfully");
        dto.setPhoneNumber(pulsa.getPhoneNumber());
        dto.setAmount(pulsa.getAmount());
        return dto;
    }
}
