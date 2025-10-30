package com.example.vms.Service;

import com.example.vms.Dto.LoginRequest;
import com.example.vms.Dto.LoginResponse;
import com.example.vms.Dto.RegisterRequest;
import com.example.vms.Dto.RegisterResponse;

import java.util.List;

public interface AuditorService {


        RegisterResponse registerAuditor(RegisterRequest request);
        RegisterResponse registerAdmin(RegisterRequest request);


    // LOGIN
    LoginResponse login(LoginRequest request);

    // READ
    RegisterResponse getAuditorById(Long id);
    List<RegisterResponse> getAllAuditors();

    // UPDATE
    RegisterResponse updateAuditor(Long id, RegisterRequest request);

    // DELETE
    String deleteAuditor(Long id);
    String deleteAllAuditors();
}
