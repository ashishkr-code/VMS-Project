package com.example.CVE.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CveRequest {

    @NotBlank(message = "version cant be blank")
    private String version;

    @NotNull(message = "package cant be blank")
    private String packageName;

    @NotEmpty(message = "write Description")
    private String description;



}