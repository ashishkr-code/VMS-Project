package com.example.vms.Service.impl;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.CveResponse;
import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Globalexception.CustomException;
import com.example.vms.Model.CveModel;
import com.example.vms.Model.Product;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Repository.ProductRepository;
import com.example.vms.Service.CveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CveServiceImpl implements CveService {

    @Autowired
    private CveRepository cveRepository;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    // CREATE
    @Override
    public CveResponse createCve(CveRequest cveRequest) {
        Product product = productRepository.findByProductId(cveRequest.getProductId())
                .orElseThrow(() -> new CustomException.CveNotFoundException(
                        "Product not found with ID: " + cveRequest.getProductId()));

        CveModel cve = new CveModel();
        cve.setPackageName(cveRequest.getPackageName());
        cve.setDescription(cveRequest.getDescription());
        cve.setSeverity(Severity.valueOf(cveRequest.getSeverity()));
        cve.setProduct(product);
        cve.setStatus(cveRequest.getStatus());
        cve.setVersion(cveRequest.getVersion());

        CveModel saved = cveRepository.save(cve);
        return modelMapper.map(saved, CveResponse.class);
    }


    // READ ALL
    @Override
    public List<CveResponse> getAllCves() {
        List<CveModel> list = cveRepository.findAll();
        List<CveResponse> responses = new ArrayList<>();
        for (CveModel cve : list) {
            responses.add(modelMapper.map(cve, CveResponse.class));
        }
        return responses;
    }


    // GET by CVE ID
    @Override
    public CveResponse getCveByCveId(String cveId) {
        CveModel cve = cveRepository.findByCveId(cveId)
                .orElseThrow(() -> new CustomException.CveNotFoundException(
                        "CVE not found with cveId: " + cveId));
        return modelMapper.map(cve, CveResponse.class);
    }


    // UPDATE
    @Override
    public CveResponse updateCve(String cveId, CveRequest cveRequest) {
        CveModel existing = cveRepository.findByCveId(cveId)
                .orElseThrow(() -> new CustomException.CveNotFoundException("CVE not found with cveId: " + cveId));

        if (cveRequest.getPackageName() != null) existing.setPackageName(cveRequest.getPackageName());
        if (cveRequest.getDescription() != null) existing.setDescription(cveRequest.getDescription());
        if (cveRequest.getSeverity() != null) existing.setSeverity(Severity.valueOf(cveRequest.getSeverity()));

        if (cveRequest.getProductId() != null) {
            Product product = productRepository.findByProductId(cveRequest.getProductId())
                    .orElseThrow(() -> new CustomException.CveNotFoundException("Product not found"));
            existing.setProduct(product);
        }

        CveModel updated = cveRepository.save(existing);
        return modelMapper.map(updated, CveResponse.class);
    }


    @Override
    public CveResponse partiallyUpdateCve(String cveId, CveRequest request) {
        //  Fetch CVE from DB
        CveModel cve = cveRepository.findByCveId(cveId)
                .orElseThrow(() -> new CustomException.CveNotFoundException("CVE not found: " + cveId));
        //  Update only the fields that are not null in request
        if (request.getPackageName() != null) {
            cve.setPackageName(request.getPackageName());
        }
        if (request.getDescription() != null) {
            cve.setDescription(request.getDescription());
        }
        if (request.getSeverity() != null) {
            cve.setSeverity(Severity.valueOf(request.getSeverity().toUpperCase()));
        }
        if (request.getStatus() != null) {
            cve.setStatus(request.getStatus());
        }
        if (request.getVersion() != null) {
            cve.setVersion(request.getVersion());
        }
        if (request.getProductId() != null) {
            Product product = productRepository.findByProductId(request.getProductId())
                    .orElseThrow(() -> new CustomException.CveNotFoundException("Product not found: " + request.getProductId()));
            cve.setProduct(product);
        }
        //  Save updated CVE
        CveModel updatedCve = cveRepository.save(cve);
        //  Map to Response DTO
        return modelMapper.map(updatedCve, CveResponse.class);
    }


    // DELETE by numeric ID
    @Override
    public void deleteCve(String cveId) {
        CveModel cve = cveRepository.findByCveId(cveId)
                .orElseThrow(() -> new CustomException.CveNotFoundException("CVE not found with cveId: " + cveId));
        cveRepository.delete(cve);
    }

    // SEARCH by Package Name
    @Override
    public List<CveResponse> getCvesByPackageName(String packageName) {
        List<CveModel> list = cveRepository.findAll();
        List<CveResponse> result = new ArrayList<>();
        for (CveModel cve : list) {
            if (cve.getPackageName() != null && cve.getPackageName().equals(packageName)) {
                result.add(modelMapper.map(cve, CveResponse.class));
            }
        }
        return result;
    }

    // SEARCH by Severity
    @Override
    public List<CveResponse> getCvesBySeverity(String severity) {
        List<CveModel> list = cveRepository.findAll();
        List<CveResponse> result = new ArrayList<>();
        for (CveModel cve : list) {
            if (cve.getSeverity() != null && cve.getSeverity().name().equalsIgnoreCase(severity)) {
                result.add(modelMapper.map(cve, CveResponse.class));
            }
        }
        return result;
    }

    // SEARCH by Description
    @Override
    public List<CveResponse> getCvesByDescription(String description) {
        List<CveModel> list = cveRepository.findAll();
        List<CveResponse> result = new ArrayList<>();
        for (CveModel cve : list) {
            if (cve.getDescription() != null && cve.getDescription().toLowerCase().contains(description.toLowerCase())) {
                result.add(modelMapper.map(cve, CveResponse.class));
            }
        }
        return result;
    }

    @Override
    public List<CveResponse> getAllCvesByStatus(String status) {
        CveStatus enumStatus = CveStatus.valueOf(status.toUpperCase());
        List<CveModel> cves = cveRepository.findByStatus(enumStatus);

        if (cves.isEmpty()) {
            throw new CustomException.CveNotFoundException("No CVEs found with status: " + status);
        }

        return cves.stream()
                .map(cve -> modelMapper.map(cve, CveResponse.class))
                .toList();
    }



    // Delete CVE by numeric ID
    @Override
    public void deleteCveById(Integer id) {
        CveModel cve = cveRepository.findById(id)
                .orElseThrow(() -> new CustomException.CveNotFoundException("CVE not found with ID: " + id));
        cveRepository.delete(cve);
    }

    // Get CVE by numeric ID
    @Override
    public CveResponse getCveById(Integer id) {
        CveModel cve = cveRepository.findById(id)
                .orElseThrow(() -> new CustomException.CveNotFoundException("CVE not found with ID: " + id));
        return modelMapper.map(cve, CveResponse.class);
    }



}
