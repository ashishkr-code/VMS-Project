package com.example.vms.Service;
import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.CveResponse;
import java.util.List;

public interface CveService {

    CveResponse createCve(CveRequest cveRequest);
    List<CveResponse> getAllCves();
    CveResponse getCveByCveId(String cveId);
    CveResponse updateCve(String cveId, CveRequest cveRequest);
    CveResponse partiallyUpdateCve(String cveId, CveRequest request);
    void deleteCve(String cveId);
    List<CveResponse> getCvesByPackageName(String packageName);
    List<CveResponse> getCvesBySeverity(String severity);
    List<CveResponse> getCvesByDescription(String description);
    List<CveResponse> getAllCvesByStatus(String status);
    void deleteCveById(Integer id);
    CveResponse getCveById(Integer id);

}
