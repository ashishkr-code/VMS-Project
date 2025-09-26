package com.example.RegisterAndLogin.Dto;


import com.example.RegisterAndLogin.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String message;
    private String username;
    private String email;
    private Role role;
}
