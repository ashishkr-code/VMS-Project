package com.example.vms.Repository;

import com.example.vms.Model.Auditor;
import com.example.vms.Model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByTokenAndAuditorEmail(int token, String email);
    void deleteAllByAuditor(Auditor auditor);
}
