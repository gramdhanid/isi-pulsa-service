package id.my.dansapps.auth.service;

public interface OtpService {
    String generateOTP(String username);
    void sendOTPViaSMS(String username, String otp);
    Boolean validateOTP(String username, String otp);
}
