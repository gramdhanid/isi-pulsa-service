package id.my.dansapps.auth.controller;


import id.my.dansapps.auth.dto.LoginRequest;
import id.my.dansapps.auth.dto.LoginResponse;
import id.my.dansapps.auth.dto.OTPRequest;
import id.my.dansapps.auth.dto.UserDTO;
import id.my.dansapps.auth.model.User;
import id.my.dansapps.auth.service.AuthService;
import id.my.dansapps.auth.service.OtpService;
import id.my.dansapps.auth.service.UserService;
import id.my.dansapps.common.exception.CustomException;
import id.my.dansapps.common.exception.ErrorCode;
import id.my.dansapps.common.util.messages.CustomResponse;
import id.my.dansapps.common.util.messages.CustomResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CustomResponseGenerator customResponseGenerator;
    private final UserService userService;
//    private final OtpService otpService;



    @GetMapping("/check")
    public CustomResponse<Object> checkApi (){
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("check", "Berhasil terhubung API");
        return customResponseGenerator.successResponse(stringMap.get("check"), HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/register")
    public CustomResponse<Object> addUser(@RequestBody UserDTO userDTO) {
        try {
            return customResponseGenerator.successResponse(userService.createUpdate(userDTO), HttpStatus.CREATED.toString());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @PostMapping("/login")
    public CustomResponse<Object> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws CustomException {
        try {
//            LoginResponse loginResponse = authService.login(loginRequest);
            if (authService.login(loginRequest)) {
//                String otp = otpService.generateOTP(loginRequest.getUsername());
//                otpService.sendOTPViaSMS(loginRequest.getUsername(), otp);
            } else {
                throw new CustomException("Wrong password", ErrorCode.GENERIC_FAILURE);
            }
//            String token = authService.getJWTToken(loginResponse);
//            response.addHeader("Authorization", token);
            return customResponseGenerator.successResponse("OTP Sent", HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

//    @PostMapping("/verify-otp")
//    public CustomResponse<Object> verifyOTP (@RequestBody OTPRequest request){
//        try {
//            String token = "";
//            if (otpService.validateOTP(request.getUsername(), request.getOtp())) {
//                token = authService.generateToken(request.getUsername());
//            }
//            return customResponseGenerator.successResponse(token, HttpStatus.OK.getReasonPhrase());
//        } catch (Exception e){
//            return customResponseGenerator.errorResponse(e.getMessage());
//        }
//    }
//
}
