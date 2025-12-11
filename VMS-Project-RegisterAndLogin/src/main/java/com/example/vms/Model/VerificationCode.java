package com.example.vms.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int token; // OTP code

    @ManyToOne
    @JoinColumn(name = "auditor_id", nullable = false)
    private Auditor auditor;

    private LocalDateTime expireDate;
}
