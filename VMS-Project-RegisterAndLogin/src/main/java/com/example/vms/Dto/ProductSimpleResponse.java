package com.example.vms.Dto;

import com.example.vms.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSimpleResponse {
    private String productId;
    private String name;
    private String modelno;
    private ProductStatus status;
}
