package id.my.dansapps.pulsa.service;

import id.my.dansapps.pulsa.dto.CallBackRequest;
import id.my.dansapps.pulsa.dto.CallBackResponse;

public interface CallbackService {
    CallBackResponse handlePulsaCallback(String transactionId, CallBackRequest callbackRequest);
}
