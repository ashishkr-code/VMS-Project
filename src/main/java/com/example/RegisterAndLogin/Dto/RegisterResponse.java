package com.example.RegisterAndLogin.Dto;


import com.example.RegisterAndLogin.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String username;
    private String email;
    private Role role;
}
