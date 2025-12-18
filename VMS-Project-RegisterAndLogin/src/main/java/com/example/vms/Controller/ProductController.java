package com.example.vms.Controller;

import com.example.vms.Dto.ProductRequest;
import com.example.vms.Dto.ProductResponse;
import com.example.vms.Dto.ProductSimpleResponse;
import com.example.vms.Responce.ApiResponse;
import com.example.vms.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "Endpoints for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create a new product", description = "Adds a new product to the system.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "A product with this ID or model number already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse created = productService.createProduct(productRequest);
        ApiResponse<ProductResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                created
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all products with CVEs", description = "Retrieves a list of all products, including their associated CVEs.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all products"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProductsWithCve() {
        List<ProductResponse> products = productService.getAllProductsWithCve();
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "All products fetched successfully",
                products
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all products without CVEs", description = "Retrieves a simplified list of all products without their associated CVE details.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all products"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/without-cve")
    public ResponseEntity<ApiResponse<List<ProductSimpleResponse>>> getAllProductsWithoutCve() {
        List<ProductSimpleResponse> products = productService.getAllProductsWithoutCve();
        ApiResponse<List<ProductSimpleResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Products (without CVE list) fetched successfully",
                products
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a product by Product ID", description = "Retrieves a specific product using its unique Product ID.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified ID not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByProductId(@PathVariable String productId) {
        ProductResponse product = productService.getProductByProductId(productId);
        ApiResponse<ProductResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product fetched successfully",
                product
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a product by Model Number", description = "Retrieves a specific product using its model number.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified model number not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/model/{modelNo}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByModelNo(@PathVariable String modelNo) {
        ProductResponse product = productService.getProductByModelNo(modelNo);
        ApiResponse<ProductResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product fetched successfully",
                product
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a product by Name", description = "Retrieves a specific product using its name.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified name not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByName(@PathVariable String name) {
        ProductResponse product = productService.getProductByName(name);
        ApiResponse<ProductResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product fetched successfully",
                product
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a product (Full)", description = "Performs a full update on an existing product. All fields must be provided.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified ID not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable String productId,
                                                                      @RequestBody ProductRequest request) {
        ProductResponse updated = productService.updateProduct(productId, request);
        ApiResponse<ProductResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Partially update a product", description = "Performs a partial update on a product. Only the fields provided in the request will be updated.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified ID not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> partiallyUpdateProduct(@PathVariable String productId,
                                                                               @RequestBody ProductRequest updates) {
        ProductResponse updated = productService.partiallyUpdateProduct(productId, updates);
        ApiResponse<ProductResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product partially updated successfully",
                updated
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a product by Product ID", description = "Deletes a product from the system using its unique Product ID.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified ID not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/productId/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductByProductId(@PathVariable String productId) {
        productService.deleteProductByProductId(productId);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Product deleted successfully",
                null
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Operation(summary = "Delete a product by numeric ID", description = "Deletes a product from the system using its numeric primary key.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product with the specified ID not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Product deleted successfully",
                null
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}