package com.example.vms.Repository;

import com.example.vms.Model.CveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CveRepository extends JpaRepository<CveModel, Integer> {
    Optional<CveModel> findByCveId(String cveId);
}