package com.example.vms.Controller;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.CveResponse;
import com.example.vms.Responce.ApiResponse;
import com.example.vms.Service.CveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cve")
@Tag(name = "CVE Management", description = "Endpoints for managing vulnerabilities and CVEs")
public class CveController {

    @Autowired
    private CveService cveAdminService;

    // CREATE
    @Operation(summary = "Create a new CVE", description = "Adds a new Common Vulnerability and Exposure entry to the system.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "CVE created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "A CVE with this ID already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CveResponse>> createCve(@RequestBody CveRequest cveRequest) {
        CveResponse created = cveAdminService.createCve(cveRequest);
        ApiResponse<CveResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "CVE created successfully",
                created
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL
    @Operation(summary = "Get all CVEs", description = "Retrieves a list of all CVE entries available in the system.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all CVEs"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<CveResponse>>> getAllCves() {
        List<CveResponse> cves = cveAdminService.getAllCves();
        ApiResponse<List<CveResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "All CVEs fetched successfully",
                cves
        );
        return ResponseEntity.ok(response);
    }

    // GET by CVE ID
    @Operation(summary = "Get a CVE by its ID", description = "Retrieves a specific CVE entry using its unique CVE-ID (e.g., CVE-2023-12345).")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved the CVE"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "CVE with the specified ID not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{cveId}")
    public ResponseEntity<ApiResponse<CveResponse>> getCveByCveId(@PathVariable String cveId) {
        CveResponse cve = cveAdminService.getCveByCveId(cveId);
        ApiResponse<CveResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "CVE fetched successfully",
                cve
        );
        return ResponseEntity.ok(response);
    }

    // UPDATE (Full Update)
    @Operation(summary = "Update a CVE (Full Update)", description = "Performs a full update on an existing CVE entry. All fields in the request body must be provided.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "CVE updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "CVE with the specified ID not found to update"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{cveId}")
    public ResponseEntity<ApiResponse<CveResponse>> updateCve(@PathVariable String cveId,
                                                              @RequestBody CveRequest cveRequest) {
        CveResponse updated = cveAdminService.updateCve(cveId, cveRequest);
        ApiResponse<CveResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "CVE updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }

    // PARTIAL UPDATE (PATCH)
    @Operation(summary = "Partially update a CVE", description = "Performs a partial update on an existing CVE entry. Only the provided fields in the request body will be updated.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "CVE partially updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "CVE with the specified ID not found to update"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{cveId}")
    public ResponseEntity<ApiResponse<CveResponse>> partiallyUpdateCve(@PathVariable String cveId,
                                                                       @RequestBody CveRequest cveRequest) {
        CveResponse updated = cveAdminService.partiallyUpdateCve(cveId, cveRequest);
        ApiResponse<CveResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "CVE partially updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }

    // DELETE by numeric ID
    @Operation(summary = "Delete a CVE", description = "Deletes a CVE entry from the system using its unique CVE-ID.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "CVE deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "CVE with the specified ID not found to delete"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{cveId}")
    public ResponseEntity<ApiResponse<Void>> deleteCve(@PathVariable String cveId) {
        cveAdminService.deleteCve(cveId);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "CVE deleted successfully",
                null
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    // SEARCH by Package Name
    @Operation(summary = "Search CVEs by package name", description = "Finds all CVEs associated with a specific package name.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved CVEs for the package"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search/package")
    public ResponseEntity<ApiResponse<List<CveResponse>>> getCvesByPackageName(@RequestParam String packageName) {
        List<CveResponse> cves = cveAdminService.getCvesByPackageName(packageName);
        ApiResponse<List<CveResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "CVEs fetched successfully by package name",
                cves
        );
        return ResponseEntity.ok(response);
    }

    // SEARCH by Severity
    @Operation(summary = "Search CVEs by severity", description = "Finds all CVEs that match a given severity level (e.g., CRITICAL, HIGH, MEDIUM, LOW).")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved CVEs for the severity"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid severity value provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search/severity")
    public ResponseEntity<ApiResponse<List<CveResponse>>> getCvesBySeverity(@RequestParam String severity) {
        List<CveResponse> cves = cveAdminService.getCvesBySeverity(severity);
        ApiResponse<List<CveResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "CVEs fetched successfully by severity",
                cves
        );
        return ResponseEntity.ok(response);
    }

    // SEARCH by Description
    @Operation(summary = "Search CVEs by description", description = "Finds all CVEs whose description contains the provided search keyword.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved CVEs matching the description"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search/description")
    public ResponseEntity<ApiResponse<List<CveResponse>>> getCvesByDescription(@RequestParam String description) {
        List<CveResponse> cves = cveAdminService.getCvesByDescription(description);
        ApiResponse<List<CveResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "CVEs fetched successfully by description",
                cves
        );
        return ResponseEntity.ok(response);
    }
}