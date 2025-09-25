package com.example.RegisterAndLogin.Service;

import com.example.RegisterAndLogin.Dto.LoginRequest;
import com.example.RegisterAndLogin.Dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuditorService {
    ResponseEntity<?> register(RegisterRequest request);
    ResponseEntity<?> login(LoginRequest request);
}
