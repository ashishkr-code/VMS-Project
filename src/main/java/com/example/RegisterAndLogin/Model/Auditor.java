package com.example.RegisterAndLogin.Model;

import com.example.RegisterAndLogin.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auditors")
public class Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)   // Role as String (ROLE_ADMIN, ROLE_AUDITOR)
    private Role role;
}
