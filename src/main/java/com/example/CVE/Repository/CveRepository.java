package com.example.CVE.Repository;

import com.example.CVE.Model.CveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CveRepository extends JpaRepository<CveModel, Integer> {
}