package com.example.RegisterAndLogin.Repository;

import com.example.RegisterAndLogin.Model.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditorRepository extends JpaRepository<Auditor, Long> {
    Optional<Auditor> findByUsernameOrEmail(String username, String email);
}
