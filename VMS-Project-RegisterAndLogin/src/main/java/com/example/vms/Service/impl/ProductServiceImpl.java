package com.example.vms.Service.impl;

import com.example.vms.Dto.ProductRequest;
import com.example.vms.Dto.ProductResponse;
import com.example.vms.Dto.ProductSimpleResponse;
import com.example.vms.Globalexception.CustomException;
import com.example.vms.Model.Product;
import com.example.vms.Repository.ProductRepository;
import com.example.vms.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    //  Create Product with auto productId
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = modelMapper.map(request, Product.class);
        long count = productRepository.count() + 1;
        product.setProductId("PRD-" + String.format("%03d", count));
        product.setModelno(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductResponse.class);
    }

    //  Get all products
    @Override
    public List<ProductResponse> getAllProductsWithCve() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();
        for (Product p : products) {
            responses.add(modelMapper.map(p, ProductResponse.class));
        }
        return responses;
    }

    //  Get product by productId
    @Override
    public ProductResponse getProductByProductId(String productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException.ProductNotFoundException("Product not found with productId: " + productId));
        return modelMapper.map(product, ProductResponse.class);
    }

    //  Get product by model number
    @Override
    public ProductResponse getProductByModelNo(String modelNo) {
        Product product = productRepository.findByModelno(modelNo)
                .orElseThrow(() -> new CustomException.ProductNotFoundException("Product not found with modelNo: " + modelNo));
        return modelMapper.map(product, ProductResponse.class);
    }

    //  Get product by name
    @Override
    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CustomException.ProductNotFoundException("Product not found with name: " + name));
        return modelMapper.map(product, ProductResponse.class);
    }

    //  Full update
    @Override
    public ProductResponse updateProduct(String productId, ProductRequest updatedProduct) {
        Product existing = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException.ProductNotFoundException("Product not found with productId: " + productId));

        modelMapper.map(updatedProduct, existing);
        Product saved = productRepository.save(existing);
        return modelMapper.map(saved, ProductResponse.class);
    }

    //  Partial update
    @Override
    public ProductResponse partiallyUpdateProduct(String productId, ProductRequest updates) {
        Product existing = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException.ProductNotFoundException("Product not found with productId: " + productId));

        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getModelno() != null) existing.setModelno(updates.getModelno());
        if (updates.getPublishat() != null) existing.setPublishAt(LocalDateTime.parse(updates.getPublishat()));
        if (updates.getStatus() != null) existing.setStatus(updates.getStatus());

        Product saved = productRepository.save(existing);
        return modelMapper.map(saved, ProductResponse.class);
    }


    //  Delete by numeric id
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id))
            throw new CustomException.ProductNotFoundException("Product not found with ID: " + id);
        productRepository.deleteById(id);
    }


    @Override
    public List<ProductSimpleResponse> getAllProductsWithoutCve() {
        List<Product> products = productRepository.findAll();
        List<ProductSimpleResponse> responses = new ArrayList<>();

        for (Product p : products) {
            ProductSimpleResponse res = new ProductSimpleResponse(
                    p.getProductId(),
                    p.getName(),
                    p.getModelno(),
                    p.getStatus()
            );
            responses.add(res);
        }

        return responses;
    }




    //  Delete by productId
    @Override
    public void deleteProductByProductId(String productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException.ProductNotFoundException("Product not found with productId: " + productId));
        productRepository.delete(product);
    }
}
