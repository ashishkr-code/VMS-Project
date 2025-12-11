package com.example.vms.UIService;

import com.example.vms.Dto.CveResponse;
import com.example.vms.Model.Auditor;
import com.example.vms.Model.CveModel;
import com.example.vms.Repository.AuditorRepository;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Repository.ProductRepository;
import com.example.vms.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CveResponse> getAllCves() {
        List<CveModel> cves = cveRepository.findAll();
        return cves.stream()
                .map(this::mapToCveResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CveResponse getCveById(Integer id) {
        Optional<CveModel> opt = cveRepository.findById(id);
        return opt.map(this::mapToCveResponse).orElse(null);
    }

    private CveResponse mapToCveResponse(CveModel cve) {
        CveResponse response = modelMapper.map(cve, CveResponse.class);
        if (cve.getProduct() != null) {
            response.setProductId(cve.getProduct().getProductId());
        }
        return response;
    }

    //  Safe method to get logged-in auditor
    public Auditor getLoggedInAuditor() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            // Check if user is authenticated
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                return null;
            }

            String email = auth.getName(); // Assuming username = email
            return auditorRepository.findByEmail(email).orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
