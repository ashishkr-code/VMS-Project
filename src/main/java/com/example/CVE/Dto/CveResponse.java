package com.example.CVE.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CveResponse {

    private int cveId;
    private String packageName;
    private LocalDate createdAt;
    private String description;

}