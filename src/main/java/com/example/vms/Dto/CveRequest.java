package com.example.vms.Dto;

import com.example.vms.Enum.CveStatus;
import com.example.vms.Version.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CveRequest {


    @NotBlank(message = "Package name is required")
    private String packageName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Severity is required")
    private String severity;
    @NotBlank(message = "Product ID is required")
    private String productId;
    @NotNull(message = "Status is required")
    private CveStatus status;  // Enum: ACTIVE / INACTIVE
    private Version version;

    public void setCveId(String cveId) {


    }
}
