package com.example.vms.Repository;

import com.example.vms.Model.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditorRepository extends JpaRepository<Auditor, Long> {
    Optional<Auditor> findByEmail(String email);
    boolean existsByEmail(String email);
}
