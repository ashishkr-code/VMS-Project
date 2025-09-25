package com.example.RegisterAndLogin.Service;

import com.example.RegisterAndLogin.Dto.*;
import com.example.RegisterAndLogin.Model.Auditor;
import com.example.RegisterAndLogin.Repository.AuditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuditorServiceImpl implements AuditorService {

    @Autowired
    private AuditorRepository auditorRepository;

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (request.getUsername() == null || request.getEmail() == null ||
                request.getPassword() == null || request.getConfirmPassword() == null || request.getRole() == null) {
            return ResponseEntity.badRequest().body(new RegisterResponse(
                    "All fields are required", null, null, null
            ));
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new RegisterResponse(
                    "Passwords do not match", null, null, null
            ));
        }

        Auditor auditor = new Auditor();
        auditor.setUsername(request.getUsername());
        auditor.setEmail(request.getEmail());
        auditor.setPassword(request.getPassword());
        auditor.setRole(request.getRole());

        auditorRepository.save(auditor);

        return ResponseEntity.ok(new RegisterResponse(
                "Registration successful",
                auditor.getUsername(),
                auditor.getEmail(),
                auditor.getRole()
        ));
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        return auditorRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .map(auditor -> {
                    if (!auditor.getPassword().equals(request.getPassword())) {
                        return ResponseEntity.status(401).body(new LoginResponse(
                                "Invalid credentials", null, null
                        ));
                    }
                    return ResponseEntity.ok(new LoginResponse(
                            "Login successful",
                            auditor.getUsername(),
                            auditor.getRole()
                    ));
                })
                .orElse(ResponseEntity.status(404).body(new LoginResponse(
                        "Auditor not found", null, null
                )));
    }
}
