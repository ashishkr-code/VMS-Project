package com.example.RegisterAndLogin.Service;

import com.example.RegisterAndLogin.Dto.*;
import com.example.RegisterAndLogin.Model.Auditor;
import com.example.RegisterAndLogin.Repository.AuditorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditorServiceImpl implements AuditorService {

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (request.getUsername() == null || request.getEmail() == null ||
                request.getPassword() == null || request.getConfirmPassword() == null || request.getRole() == null) {
            return new RegisterResponse("All fields are required", null, null, null);
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new RegisterResponse("Passwords do not match", null, null, null);
        }

        // DTO → Entity mapping
        Auditor auditor = modelMapper.map(request, Auditor.class);

        auditorRepository.save(auditor);

        // Entity → Response DTO mapping
        RegisterResponse response = modelMapper.map(auditor, RegisterResponse.class);
        response.setMessage("Registration successful");
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Auditor auditor = auditorRepository
                .findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElse(null);

        if (auditor == null) {
            return new LoginResponse("Auditor not found", null, null);
        }

        if (!auditor.getPassword().equals(request.getPassword())) {
            return new LoginResponse("Invalid credentials", null, null);
        }

        LoginResponse response = modelMapper.map(auditor, LoginResponse.class);
        response.setMessage("Login successful");
        return response;
    }
}
