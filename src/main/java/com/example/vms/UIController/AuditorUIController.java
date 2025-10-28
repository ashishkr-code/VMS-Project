package com.example.vms.UIController;

import com.example.vms.Model.CveModel;
import com.example.vms.Model.Product;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuditorUIController {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ProductRepository productRepository;

    //  Main auditor dashboard page
    @GetMapping("/auditor/dashboard")
    public String auditorDashboard(Model model) {
        List<CveModel> cves = cveRepository.findAll();
        List<Product> products = productRepository.findAll();

        model.addAttribute("cves", cves);
        model.addAttribute("products", products);

        return "auditor/dashboard"; // thymeleaf page name
    }
}
