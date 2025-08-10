package id.my.dansapps.pulsa.controller;

import id.my.dansapps.common.util.messages.CustomResponse;
import id.my.dansapps.common.util.messages.CustomResponseGenerator;
import id.my.dansapps.pulsa.dto.PulsaRequest;
import id.my.dansapps.pulsa.service.PulsaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulsa")
public class PulsaController {

    private final PulsaService pulsaService;
    private final CustomResponseGenerator customResponseGenerator;

    @PostMapping("/buy")
    public CustomResponse<Object> buyPulsa(@RequestBody PulsaRequest request) {
        try {
            return customResponseGenerator.successResponse(pulsaService.beliPulsa(request), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e) {
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @GetMapping("/status/{transactionId}")
    public CustomResponse<Object> checkStatus(@PathVariable String transactionId) {
        try {
            return customResponseGenerator.successResponse(pulsaService.checkStatus(transactionId), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }
}
