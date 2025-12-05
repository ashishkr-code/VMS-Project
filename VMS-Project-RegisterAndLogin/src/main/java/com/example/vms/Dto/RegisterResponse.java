package com.example.vms.Dto;


import com.example.vms.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String username;
    private String email;
    private Role role;
    LocalDateTime created_at;


    public RegisterResponse(String auditorUpdatedSuccessfully, String username, String email, Role role) {


    }
}

