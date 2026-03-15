package com.ecommerce.project_backend.controller;

import com.ecommerce.project_backend.dto.ProductRequestDTO;
import com.ecommerce.project_backend.dto.ProductResponseDTO;
//import com.ecommerce.project_backend.entity.Product;
import com.ecommerce.project_backend.service.ProductService;

//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add product
    @Operation(summary = "Add new product")
    @PostMapping
    public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO dto){
        return productService.addProduct(dto);
    }

    // Get all products
    @Operation(summary = "Get product detail")
    @GetMapping
    public Page<ProductResponseDTO> getProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    // Get product by ID
    @Operation(summary = "Get producr by ID")
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    // Update product
    @Operation(summary = "Update product")
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id,
                                            @RequestBody ProductRequestDTO dto){
        return productService.updateProduct(id, dto);
    }

    // Delete product
    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
