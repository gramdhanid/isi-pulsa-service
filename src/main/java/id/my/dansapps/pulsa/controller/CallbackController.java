package id.my.dansapps.pulsa.controller;

import id.my.dansapps.common.util.messages.CustomResponse;
import id.my.dansapps.common.util.messages.CustomResponseGenerator;
import id.my.dansapps.pulsa.dto.CallBackRequest;
import id.my.dansapps.pulsa.service.CallbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/callback")
@Slf4j
@RequiredArgsConstructor
public class CallbackController {

    private final CustomResponseGenerator customResponseGenerator;
    private final CallbackService callbackService;

    @PostMapping("/pulsa/{transactionId}")
    public CustomResponse<Object> handlePulsaCallback(
            @PathVariable String transactionId,
            @RequestBody CallBackRequest callbackRequest) {

        log.info("Received callback for transaction: {}, status: {}",
                transactionId, callbackRequest.getStatus());
        try {
            return customResponseGenerator.successResponse(callbackService.handlePulsaCallback(transactionId, callbackRequest), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }
}
