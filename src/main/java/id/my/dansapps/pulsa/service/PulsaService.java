package id.my.dansapps.pulsa.service;

import id.my.dansapps.pulsa.dto.PulsaCheck;
import id.my.dansapps.pulsa.dto.PulsaRequest;
import id.my.dansapps.pulsa.dto.PulsaResponse;

public interface PulsaService {

    PulsaResponse beliPulsa(PulsaRequest request);
    String detectOperator(String phoneNumber);
    String buildCallbackUrl(String transactionId);
    String generateTransactionId();
    PulsaCheck checkStatus(String transactionId);

}
