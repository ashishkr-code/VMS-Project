package com.example.vms.Service;

import com.example.vms.Dto.ProductRequest;
import com.example.vms.Dto.ProductResponse;
import com.example.vms.Dto.ProductSimpleResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProductsWithCve();

    ProductResponse getProductByProductId(String productId);

    ProductResponse getProductByModelNo(String modelNo);

    ProductResponse getProductByName(String name);

    ProductResponse updateProduct(String productId, ProductRequest request);

    ProductResponse partiallyUpdateProduct(String productId, ProductRequest updates);

    void deleteProduct(Long id);

    void deleteProductByProductId(String productId);

    List<ProductSimpleResponse> getAllProductsWithoutCve();
}
