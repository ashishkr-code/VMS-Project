package com.example.RegisterAndLogin.Dto;

import com.example.RegisterAndLogin.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String message;
    private String username;
    private Role role;
}
