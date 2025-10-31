package com.example.vms.Dto;

import com.example.vms.Enum.ProductStatus;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String modelno;
    private String publishat;
    private ProductStatus status;
}
