package com.example.RegisterAndLogin.Service;

import com.example.RegisterAndLogin.Dto.LoginRequest;
import com.example.RegisterAndLogin.Dto.LoginResponse;
import com.example.RegisterAndLogin.Dto.RegisterRequest;
import com.example.RegisterAndLogin.Dto.RegisterResponse;

public interface AuditorService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    String deleteAuditor(Long id);
}
