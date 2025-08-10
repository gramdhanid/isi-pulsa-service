//package id.my.dansapps.auth.service.impl;
//
//import id.my.dansapps.auth.service.OtpService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//@Service
//@RequiredArgsConstructor
//public class OtpImpl implements OtpService {
//
//    private final StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public String generateOTP(String username) {
//        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
//        stringRedisTemplate.opsForValue().set("OTP:" + username, otp, 5, TimeUnit.MINUTES);
//        return otp;
//    }
//
//    @Override
//    public void sendOTPViaSMS(String username, String otp) {
//        System.out.println("Kirim OTP ke user " + username + ": " + otp);
//    }
//
//    @Override
//    public Boolean validateOTP(String username, String otp) {
//        String key = "OTP:" + username;
//        String savedOtp = stringRedisTemplate.opsForValue().get(key);
//        return otp.equals(savedOtp);
//    }
//}
