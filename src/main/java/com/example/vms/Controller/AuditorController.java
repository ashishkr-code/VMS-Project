package com.example.vms.Controller;

import com.example.vms.Dto.LoginRequest;
import com.example.vms.Dto.LoginResponse;
import com.example.vms.Dto.RegisterRequest;
import com.example.vms.Dto.RegisterResponse;
import com.example.vms.Globalexception.CustomException;
import com.example.vms.Responce.ApiResponse;
import com.example.vms.Service.AuditorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditor")
@Tag(name = "User Management", description = "Endpoints for managing auditors")
public class AuditorController {

    @Autowired
    private AuditorService auditorService;

    // REGISTER Auditor
    @Operation(summary = "Register a new auditor", description = "Creates a new auditor account")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Auditor registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Email already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerAuditor(
            @Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = auditorService.registerAuditor(request);
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Auditor registered successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // REGISTER Admin
    @Operation(summary = "Register a new admin", description = "Create a new admin")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Admin registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Email already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerAdmin(
            @Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = auditorService.registerAdmin(request);
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Admin registered successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // LOGIN
    @Operation(summary = "Login", description = "Login with email and password")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid email/password"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = auditorService.login(request);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                "Login successful", response);
        return ResponseEntity.ok(apiResponse);
    }

    // GET Auditor by ID
    @Operation(summary = "Get auditor by ID", description = "Fetch auditor details by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Auditor fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Auditor not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RegisterResponse>> getAuditorById(@PathVariable Long id) {
        RegisterResponse response = auditorService.getAuditorById(id);
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                "Auditor fetched successfully", response);
        return ResponseEntity.ok(apiResponse);
    }

    // GET All Auditors
    @Operation(summary = "Get all auditors", description = "Fetch a list of all auditors")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All auditors fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RegisterResponse>>> getAllAuditors() {
        List<RegisterResponse> auditors = auditorService.getAllAuditors();
        ApiResponse<List<RegisterResponse>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
                "All auditors fetched successfully", auditors);
        return ResponseEntity.ok(apiResponse);
    }

    // UPDATE Auditor
    @Operation(summary = "Update auditor", description = "Update auditor details by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Auditor updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or no valid fields provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Auditor not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict in updating data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RegisterResponse>> updateAuditor(
            @PathVariable Long id,
            @RequestBody RegisterRequest request) {

        if ((request.getUsername() == null || request.getUsername().isBlank()) &&
                (request.getPassword() == null || request.getPassword().isBlank()) &&
                request.getRole() == null) {
            throw new CustomException.InvalidRequestException(
                    "No valid fields provided for update. Only username, password, and role can be updated."
            );
        }

        RegisterResponse response = auditorService.updateAuditor(id, request);

        ApiResponse<RegisterResponse> successResponse = new ApiResponse<>(HttpStatus.OK.value(),
                "Auditor updated successfully", response);

        return ResponseEntity.ok(successResponse);
    }

    // DELETE Auditor by ID
    @Operation(summary = "Delete auditor", description = "Delete an auditor by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Auditor deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Auditor not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAuditor(@PathVariable Long id) {
        String msg = auditorService.deleteAuditor(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), msg);
        return ResponseEntity.ok(apiResponse);
    }

    // DELETE All Auditors
    @Operation(summary = "Delete all auditors", description = "Delete all auditor records")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "All auditors deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse<String>> deleteAllAuditors() {
        String msg = auditorService.deleteAllAuditors();
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), msg);
        return ResponseEntity.ok(apiResponse);
    }
}
