package com.example.vms.Service.impl;

import com.example.vms.Dto.*;
import com.example.vms.Enum.Role;
import com.example.vms.Globalexception.CustomException;
import com.example.vms.Model.Auditor;
import com.example.vms.Repository.AuditorRepository;
import com.example.vms.Service.AuditorService;
import com.example.vms.Utility.RegistrationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuditorServiceImpl implements AuditorService {

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VerificationCodeServiceImpl verificationCodeService;


    @Override
    public RegisterResponse registerAuditor(RegisterRequest request) {
        try {
            request.setRole(Role.ROLE_AUDITOR);
            return registerUser(request);
        } catch (IllegalArgumentException e) {
            throw new CustomException.RegistrationFailedException("Auditor registration failed: " + e.getMessage());
        }
    }

    @Override
    public RegisterResponse registerAdmin(RegisterRequest request) {
        try {
            request.setRole(Role.ROLE_ADMIN);
            return registerUser(request);
        } catch (IllegalArgumentException e) {
            throw new CustomException.RegistrationFailedException("Admin registration failed: " + e.getMessage());
        }
    }

    private RegisterResponse registerUser(RegisterRequest request) {
        // Email validation & duplicate check
        RegistrationUtil.validateRegistration(
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword(),
                auditorRepository
        );
        // Check if email already exists
        if (auditorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException.RegistrationFailedException("Email already registered: " + request.getEmail());
        }
        Auditor auditor = modelMapper.map(request, Auditor.class);
        Auditor saved = auditorRepository.save(auditor);
        verificationCodeService.sendVerificationCode(saved.getEmail());
        return modelMapper.map(saved, RegisterResponse.class);
    }


    // LOGIN
    @Override
    public LoginResponse login(LoginRequest request) {
        // Fetch auditor by email
        Auditor auditor = auditorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException.UserNotFoundException(
                        "Employee not found with email: " + request.getEmail()
                ));
        // Password check (plain text)
        if (!auditor.getPassword().equals(request.getPassword())) {
            throw new CustomException.LoginFailedException("Invalid Password");
        }
        // Successful login
        return new LoginResponse(
                "Login successful",
                auditor.getUsername(),
                auditor.getRole()
        );
    }

    @Override
    public RegisterResponse getAuditorById(Long id) {
        // Step 1: DB se auditor fetch karo
        Auditor auditor = auditorRepository.findById(id)
                .orElseThrow(() -> new CustomException.UserNotFoundException("Auditor not found with id: " + id));

        // Step 2: Auditor → RegisterResponse map karo manually
        RegisterResponse response = new RegisterResponse();
        response.setUsername(auditor.getUsername());
        response.setEmail(auditor.getEmail());
        response.setRole(auditor.getRole());
        response.setCreated_at(auditor.getCreated_at());
        // Step 4: response return karo
        return response;
    }


    // READ by ID
    @Override
    public List<RegisterResponse> getAllAuditors() {
        List<Auditor> auditors = auditorRepository.findAll();
        List<RegisterResponse> responseList = new ArrayList<>();
        for (Auditor auditor : auditors) {
            // ModelMapper se direct convert kar rahe hain
            RegisterResponse response = modelMapper.map(auditor, RegisterResponse.class);
            responseList.add(response);
        }
        return responseList;
    }


    // UPDATE
    @Override
    public RegisterResponse updateAuditor(Long id, RegisterRequest request) {
        Auditor auditor = auditorRepository.findById(id)
                .orElseThrow(() -> new CustomException.UserNotFoundException("Auditor not found with id: " + id));

        //  Allow only username, password, role
        boolean isUpdated = false;

        // Check if extra fields are being modified (email, mobile, etc.)
        if (request.getEmail() != null || request.getMobileno() != null || request.getConfirmPassword() != null) {
            throw new CustomException.InvalidRequestException("Only username, password, and role can be updated.");
        }

        // Validate and update username
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            auditor.setUsername(request.getUsername());
            isUpdated = true;
        }

        // Validate and update password
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            auditor.setPassword(request.getPassword());
            isUpdated = true;
        }

        // Validate and update role
        if (request.getRole() != null) {
            auditor.setRole(request.getRole());
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new CustomException.InvalidRequestException("No valid fields provided to update.");
        }

        Auditor updated = auditorRepository.save(auditor);
        RegisterResponse response = modelMapper.map(updated, RegisterResponse.class);
        return response;
    }


    // DELETE by ID
    @Override
    public String deleteAuditor(Long id) {
        Auditor auditor = auditorRepository.findById(id)
                .orElseThrow(() -> new CustomException.UserNotFoundException("Employee not found with id: " + id));

        //  Delete all OTPs linked to this auditor first
        if (auditor.getVerificationCodes() != null && !auditor.getVerificationCodes().isEmpty()) {
            auditor.getVerificationCodes().clear(); // orphanRemoval=true se ye delete ho jayega
            auditorRepository.save(auditor); // changes persist karo
        }

        //  Now delete auditor
        auditorRepository.deleteById(id);
        return "Employee deleted successfully: " + id;
    }

    // DELETE all auditors
    @Override
    public String deleteAllAuditors() {
        auditorRepository.deleteAll();
        return "All Employee deleted successfully";
    }
}

