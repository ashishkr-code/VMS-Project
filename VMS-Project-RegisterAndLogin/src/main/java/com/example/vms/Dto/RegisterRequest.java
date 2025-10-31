package com.example.vms.Dto;

import com.example.vms.Enum.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be 3 characters ")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @NotNull(message = "Mobile number required")
    @Size(min = 10,message = "Mobile number should be 10 digits")
    private String mobileno;

    private Role role;

    private String companyCode;
}
