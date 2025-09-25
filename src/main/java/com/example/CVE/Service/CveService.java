package com.example.CVE.Service;

import com.example.CVE.Dto.CveRequest;
import com.example.CVE.Dto.CveResponse;

import java.util.List;

public interface CveService {
    CveResponse createCve(CveRequest cveRequestDto);
    List<CveResponse> getAllCves();
    CveResponse updateCve(String cveId, CveRequest cveRequestDto);
    void deleteCve(String cveId);
}