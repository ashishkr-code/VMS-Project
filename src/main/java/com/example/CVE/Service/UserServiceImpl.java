package com.example.CVE.Service;

import com.example.CVE.Dto.CveResponse;
import com.example.CVE.Model.CveModel;
import com.example.CVE.Repository.CveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CveResponse> getAllCves() {
        // Database se saare CveModel objects fetch karein
        List<CveModel> cves = cveRepository.findAll();

        // CveModel list ko CveResponse list mein convert karein
        return cves.stream()
                .map(cve -> modelMapper.map(cve, CveResponse.class))
                .collect(Collectors.toList());
    }
}