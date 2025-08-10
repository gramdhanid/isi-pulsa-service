package id.my.dansapps.auth.dto;

import lombok.Data;

@Data
public class OTPRequest {
    private String username;
    private String otp;
}
