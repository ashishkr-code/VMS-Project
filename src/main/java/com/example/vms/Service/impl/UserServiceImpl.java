package com.example.vms.Service.impl;

import com.example.vms.Dto.CveResponse;
import com.example.vms.Model.CveModel;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<CveResponse> getAllCves() {
        // Database se saare CveModel objects fetch karein
        List<CveModel> cves = cveRepository.findAll();

        // CveModel list ko CveResponse list mein convert karein
        List<CveResponse> responseList = new ArrayList<>();

        for (CveModel cve : cves) {
            CveResponse response = modelMapper.map(cve, CveResponse.class);
            responseList.add(response);
        }

        return responseList;
    }
}
