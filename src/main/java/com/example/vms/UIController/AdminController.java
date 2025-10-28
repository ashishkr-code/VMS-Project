package com.example.vms.UIController;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.ProductRequest;
import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Service.ProductService;
import com.example.vms.UIService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    private CveRepository cveRepository;
    private ProductService productService;


    //  Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("cveList", adminService.getAllCves());
        model.addAttribute("productList", adminService.getAllProducts());
        model.addAttribute("userList",adminService.getAllUser());
        model.addAttribute("totalCves", adminService.getAllCves().size());
        model.addAttribute("activeCves", adminService.countByStatus(CveStatus.ACTIVE));
        model.addAttribute("patchedCves", adminService.countByStatus(CveStatus.INACTIVE));
        model.addAttribute("newCve", new CveRequest());

        //  Add severityCountMap to fix Thymeleaf chart bindings
        Map<String, Long> severityCountMap = new HashMap<>();
        for (Severity severity : Severity.values()) {
            long count = cveRepository.countBySeverity(severity);
            severityCountMap.put(severity.name(), count);
        }
        model.addAttribute("severityCountMap", severityCountMap);

        return "admin/dashboard";
    }

    //  Add CVE
    @PostMapping("/save-cve")
    public String saveCve(@ModelAttribute("newCve") CveRequest request) {
        adminService.saveCve(request);
        return "redirect:/admin/dashboard";
    }

    //  Delete CVE
    @GetMapping("/delete-cve/{id}")
    public String deleteCve(@PathVariable Integer id) {
        adminService.deleteCve(id);
        return "redirect:/admin/dashboard";
    }

    //  Update CVE (Edit Modal)
    @PostMapping("/update-cve")
    public String updateCve(@RequestParam Integer id,
                            @RequestParam String packageName,
                            @RequestParam String severity,
                            @RequestParam CveStatus status) {

        var cve = cveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CVE not found with ID: " + id));

        try {
            cve.setPackageName(packageName);
            cve.setSeverity(Severity.valueOf(severity.toUpperCase()));
            cve.setStatus(status);
            cveRepository.save(cve);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid severity: " + severity);
        }

        return "redirect:/admin/dashboard";
    }
    @PostMapping("/admin/save-product")
    public String saveProduct(@ModelAttribute ProductRequest req) {
        productService.createProduct(req);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/dashboard";
    }

}
