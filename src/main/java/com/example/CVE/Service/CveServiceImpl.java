package com.example.CVE.Service;

import com.example.CVE.Dto.CveRequest;
import com.example.CVE.Dto.CveResponse;
import com.example.CVE.Model.CveModel;
import com.example.CVE.Repository.CveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CveServiceImpl implements CveService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CveResponse createCve(CveRequest cveRequestDto) {
        CveModel cve = modelMapper.map(cveRequestDto, CveModel.class);
        cve.setCreatedAt(LocalDate.now());
        CveModel savedCve = cveRepository.save(cve);
        return modelMapper.map(savedCve, CveResponse.class);
    }

    @Override
    public List<CveResponse> getAllCves() {
        List<CveModel> cves = cveRepository.findAll();
        return cves.stream()
                .map(cve -> modelMapper.map(cve, CveResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CveResponse updateCve(String cveId, CveRequest cveRequestDto) {
        // ID type ko String kiya gaya hai
        CveModel existingCve = cveRepository.findById(Integer.valueOf(cveId))
                .orElseThrow(() -> new RuntimeException("CVE not found with ID: " + cveId));

        existingCve.setVersion(cveRequestDto.getVersion());
        existingCve.setPackageName(cveRequestDto.getPackageName());
        existingCve.setDescription(cveRequestDto.getDescription());

        CveModel updatedCve = cveRepository.save(existingCve);
        return modelMapper.map(updatedCve, CveResponse.class);
    }

    @Override
    public void deleteCve(String cveId) {
        // ID type ko String kiya gaya hai
        cveRepository.deleteById(Integer.valueOf(cveId));
    }
}