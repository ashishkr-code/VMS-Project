package com.example.vms.Dto;

import com.example.vms.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String productId;
    private String name;
    private String modelno;


    private String publishat;
    private ProductStatus status;

    private List<CveResponse> cveList;
}
