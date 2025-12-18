package com.example.vms.Repository;

import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Model.CveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CveRepository extends JpaRepository<CveModel, Integer> {

    Optional<CveModel> findByCveId(String cveId);

    List<CveModel> findByStatus(CveStatus status);

    // ✅ Count CVEs by status
    long countByStatus(CveStatus status);

    // ✅ Count CVEs by severity (for dashboard charts)
    long countBySeverity(Severity severity);

    // ✅ Search functionality
    List<CveModel> findByCveIdContainingOrPackageNameContainingOrDescriptionContaining(
            String cve, String pkg, String desc
    );
}
