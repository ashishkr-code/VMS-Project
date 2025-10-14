package com.example.vms.Service;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.CveResponse;

import java.util.List;
import java.util.Map;

public interface CveService {

    // CREATE
    CveResponse createCve(CveRequest cveRequest);

    // READ
    List<CveResponse> getAllCves();
    CveResponse getCveByCveId(String cveId);

    // UPDATE (Full Update)
    CveResponse updateCve(String cveId, CveRequest cveRequest);
    CveResponse partiallyUpdateCve(String cveId, CveRequest request);

    // DELETE
    void deleteCve(String cveId);

    // SEARCH
    List<CveResponse> getCvesByPackageName(String packageName);
    List<CveResponse> getCvesBySeverity(String severity);
    List<CveResponse> getCvesByDescription(String description);
}
