package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.ProductRequestDTO;
import com.ecommerce.project_backend.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import java.util.List;

public interface ProductService {

    ProductResponseDTO addProduct(ProductRequestDTO dto);

    //List<ProductResponseDTO> getAllProducts();
    Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);

    void deleteProduct(Long id);
}
