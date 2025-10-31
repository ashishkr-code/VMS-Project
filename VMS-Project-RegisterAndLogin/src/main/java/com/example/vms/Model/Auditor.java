package com.example.vms.Model;

import com.example.vms.Enum.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "auditors")
@EntityListeners(AuditingEntityListener.class)
public class Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private Long mobileno;
    private boolean verfied = false;
    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "auditor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationCode> verificationCodes;
}
