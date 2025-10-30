package com.example.vms.UIService;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.ProductRequest;
import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.ProductStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Model.Auditor;
import com.example.vms.Model.CveModel;
import com.example.vms.Model.Product;
import com.example.vms.Repository.AuditorRepository;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AdminService {

    private final CveRepository cveRepo;
    private final ProductRepository productRepo;
    private final AuditorRepository auditorRepository;

    @Autowired
    private HttpSession session; //  to access logged-in admin info

    public AdminService(CveRepository cveRepo,
                        ProductRepository productRepo,
                        AuditorRepository auditorRepository) {
        this.cveRepo = cveRepo;
        this.productRepo = productRepo;
        this.auditorRepository = auditorRepository;
    }

    //  Fetch all CVEs
    public List<CveModel> getAllCves() {
        return cveRepo.findAll();
    }

    //  Fetch all users (Auditors/Admins)
    public List<Auditor> getAllUser() {
        return auditorRepository.findAll();
    }

    //  Count CVEs by status
    public long countByStatus(CveStatus status) {
        return cveRepo.countByStatus(status);
    }

    //  Save new CVE
    public void saveCve(CveRequest request) {
        Product product = productRepo.findByProductId(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with Product ID: " + request.getProductId()));

        CveModel cve = new CveModel();
        cve.setCveId("CVE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        cve.setPackageName(request.getPackageName());
        cve.setDescription(request.getDescription());

        try {
            cve.setSeverity(Severity.valueOf(request.getSeverity().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid severity value: " + request.getSeverity());
        }

        cve.setStatus(request.getStatus());
        cve.setVersion(request.getVersion());
        cve.setProduct(product);
        cveRepo.save(cve);
    }

    //  Delete CVE
    public void deleteCve(Integer id) {
        if (!cveRepo.existsById(id)) {
            throw new RuntimeException("CVE not found with ID: " + id);
        }
        cveRepo.deleteById(id);
    }

    //  Update CVE
    public void updateCve(Integer id, String packageName, String severity, CveStatus status) {
        CveModel cve = cveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CVE not found with ID: " + id));

        cve.setPackageName(packageName);

        try {
            cve.setSeverity(Severity.valueOf(severity.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid severity value: " + severity);
        }

        cve.setStatus(status);
        cveRepo.save(cve);
    }

    //  Get all products
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    //  Add New Product (auto generate IDs, modelno, publish date)
    public void saveProduct(ProductRequest req) {
        Product product = new Product();

        // Auto-generate Product ID -> PRD-XXX
        String productId = "PRD-" + String.format("%03d", new Random().nextInt(999));
        product.setProductId(productId);

        // Auto-generate model number (8-char hex)
        String modelNo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        product.setModelno(modelNo);

        product.setName(req.getName());
        product.setStatus(req.getStatus() != null ? req.getStatus() : ProductStatus.UNPUBLISH);

        // Auto-set publish date = current date/time
        product.setPublishAt(LocalDateTime.now());

        productRepo.save(product);
    }

    //  Delete Product
    public void deleteProduct(Long id) {
        Optional<Product> productOpt = productRepo.findById(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepo.deleteById(id);
    }

    //  Update Product
    public void updateProduct(Long id, String name, ProductStatus status) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        product.setName(name);
        product.setStatus(status);
        product.setUpdated_at(LocalDateTime.now());

        productRepo.save(product);
    }


    //  Additional helper methods for dashboard profile

    private Auditor getLoggedAdmin() {
        return (Auditor) session.getAttribute("loggedInAuditor");
    }

    public String getUsername() {
        Auditor admin = getLoggedAdmin();
        return admin != null ? admin.getUsername() : "Admin";
    }

    public String getEmail() {
        Auditor admin = getLoggedAdmin();
        return admin != null ? admin.getEmail() : "N/A";
    }

    public String getRole() {
        Auditor admin = getLoggedAdmin();
        return admin != null ? admin.getRole().name() : "N/A";
    }

    public String getCreatedAt() {
        Auditor admin = getLoggedAdmin();
        return admin != null && admin.getCreated_at() != null
                ? admin.getCreated_at().toString()
                : "Unknown";
    }
}
