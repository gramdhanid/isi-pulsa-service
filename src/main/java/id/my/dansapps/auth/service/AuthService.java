package id.my.dansapps.auth.service;

import id.my.dansapps.auth.dto.LoginRequest;
import id.my.dansapps.auth.dto.LoginResponse;
import org.springframework.stereotype.Service;

public interface AuthService {
    Boolean login (LoginRequest loginRequest);
    String generateToken(String username);

}
