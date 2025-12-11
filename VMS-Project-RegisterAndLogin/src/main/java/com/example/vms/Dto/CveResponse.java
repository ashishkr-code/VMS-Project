package com.example.vms.Dto;

import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.Severity;
import com.example.vms.Version.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime; // ✅ Import LocalDateTime

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CveResponse {
    private String cveId;
    private String packageName;
    private String description;
    private Severity severity;
    private CveStatus status;
    private String productId;
    private Version version;
    private LocalDateTime createdAt; // ✅ Added this field to match the template
}