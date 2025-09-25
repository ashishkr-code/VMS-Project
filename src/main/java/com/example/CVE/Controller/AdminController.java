package com.example.CVE.Controller;


import com.example.CVE.Dto.CveRequest;
import com.example.CVE.Dto.CveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/cves")
public class AdminController {

    @Autowired
    private com.example.CVE.Service.CveService cveService;


    @PostMapping
    public ResponseEntity<CveResponse> createCve(@RequestBody CveRequest cveRequest) {
        CveResponse newCve = cveService.createCve(cveRequest);
        return new ResponseEntity<>(newCve, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<CveResponse>> getAllCves() {
        List<CveResponse> cves = cveService.getAllCves();
        return new ResponseEntity<>(cves, HttpStatus.OK);
    }


    @PutMapping("/{cveId}")
    public ResponseEntity<CveResponse> updateCve(@PathVariable String cveId, @RequestBody CveRequest cveRequest) {
        CveResponse updatedCve = cveService.updateCve(cveId, cveRequest);
        return new ResponseEntity<>(updatedCve, HttpStatus.OK);
    }


    @DeleteMapping("/{cveId}")
    public ResponseEntity<Void> deleteCve(@PathVariable String cveId) {
        cveService.deleteCve(cveId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}