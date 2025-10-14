package com.example.vms.Dto;

import com.example.vms.Enum.CveStatus;
import com.example.vms.Version.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CveResponse {
    private String cveid;
    private String packageName;
    private String description;
    private String severity;
    private String productId;    // productId of the related product
    private CveStatus status;
    private Version version;
}
