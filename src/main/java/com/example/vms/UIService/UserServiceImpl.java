package com.example.vms.UIService;

import com.example.vms.Dto.CveResponse;
import com.example.vms.Model.CveModel;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;

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

        // safe fallbacks in case field names differ
        if (response.getPackageName() == null || response.getPackageName().isEmpty()) {
            // if CveModel uses product field, ensure DTO has it set
            try {
                // assume CveModel#getProduct or getProductId exists
                if (cve.getProduct() != null) {
                    response.setPackageName(String.valueOf(cve.getProduct()));
                }
            } catch (Exception ignored) {}
        }

        return response;
    }
}
