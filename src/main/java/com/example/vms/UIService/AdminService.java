package com.example.vms.UIService;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Model.Auditor;
import com.example.vms.Model.CveModel;
import com.example.vms.Model.Product;
import com.example.vms.Repository.AuditorRepository;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private CveRepository cveRepo;
    private ProductRepository productRepo;
    private AuditorRepository auditorRepository;

    //  Get all CVEs
    public List<CveModel> getAllCves() {
        return cveRepo.findAll();
    }

    public List<Auditor> getAllUser(){
        return auditorRepository.findAll();
    }

    //  Count CVEs by status
    public long countByStatus(CveStatus status) {
        return cveRepo.countByStatus(status);
    }

    //  Add new CVE
    public void saveCve(CveRequest request) {
        //  Find product using productId (like "PRD-001")
        Product product = productRepo.findByProductId(request.getProductId())
                .orElseThrow(() -> new RuntimeException(" Product not found with Product ID: " + request.getProductId()));

        //  Create and populate CVE entity
        CveModel cve = new CveModel();
        cve.setCveId("CVE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        cve.setPackageName(request.getPackageName());
        cve.setDescription(request.getDescription());

        try {
            cve.setSeverity(Severity.valueOf(request.getSeverity().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(" Invalid severity value: " + request.getSeverity());
        }

        cve.setStatus(request.getStatus());
        cve.setVersion(request.getVersion());
        cve.setProduct(product);

        //  createdAt handled by @CreationTimestamp automatically
        cveRepo.save(cve);
    }

    //  Delete CVE by ID
    public void deleteCve(Integer id) {
        if (!cveRepo.existsById(id)) {
            throw new RuntimeException(" CVE not found with ID: " + id);
        }
        cveRepo.deleteById(id);
    }

    //  Update CVE (used in edit modal)
    public void updateCve(Integer id, String packageName, String severity, CveStatus status) {
        CveModel cve = cveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(" CVE not found with ID: " + id));

        cve.setPackageName(packageName);

        try {
            cve.setSeverity(Severity.valueOf(severity.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(" Invalid severity value: " + severity);
        }

        cve.setStatus(status);
        cveRepo.save(cve);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


}
