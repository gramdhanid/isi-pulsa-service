package id.my.dansapps.auth.service.impl;

import id.my.dansapps.auth.dto.LoginRequest;
import id.my.dansapps.auth.dto.LoginResponse;
import id.my.dansapps.auth.model.User;
import id.my.dansapps.auth.repository.UserRepository;
import id.my.dansapps.auth.service.AuthService;
import id.my.dansapps.common.exception.CustomException;
import id.my.dansapps.common.exception.ErrorCode;
import id.my.dansapps.common.util.messages.ErrorMessages;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {

    private final UserRepository userRepository;
    private final Argon2PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;
    private final long expirationMs = 86400000;

    @Override
    public Boolean login(LoginRequest loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
                .map(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .orElseThrow(() -> new CustomException(ErrorMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
    }

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private LoginResponse toDTO(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(user.getUsername());
        loginResponse.setFullName(user.getFullName());
        return loginResponse;
    }

}
