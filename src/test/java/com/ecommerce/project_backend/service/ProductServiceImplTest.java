package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.ProductRequestDTO;
import com.ecommerce.project_backend.dto.ProductResponseDTO;
import com.ecommerce.project_backend.entity.Product;
import com.ecommerce.project_backend.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    // TEST ADD PRODUCT
    @Test
    void testAddProduct(){

        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName("Laptop");
        dto.setDescription("Gaming Laptop");
        dto.setPrice(50000);
        dto.setStock(10);

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("Gaming Laptop");
        product.setPrice(50000);
        product.setStock(10);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Laptop");
        responseDTO.setPrice(50000);
        responseDTO.setStock(10);

        when(modelMapper.map(dto, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductResponseDTO.class)).thenReturn(responseDTO);

        ProductResponseDTO response = productService.addProduct(dto);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());

        verify(productRepository, times(1)).save(product);
    }

    // TEST GET PRODUCT BY ID
    @Test
    void testGetProductById(){

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(50000);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Laptop");
        responseDTO.setPrice(50000);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapper.map(product, ProductResponseDTO.class)).thenReturn(responseDTO);

        ProductResponseDTO response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());

        verify(productRepository, times(1)).findById(1L);
    }

    // TEST GET ALL PRODUCTS
    @Test
    void testGetAllProducts(){

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(50000);

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(1L);
        dto.setName("Laptop");
        dto.setPrice(50000);

        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(modelMapper.map(product, ProductResponseDTO.class)).thenReturn(dto);

        Page<ProductResponseDTO> result =
                productService.getAllProducts(PageRequest.of(0,5));

        assertEquals(1, result.getTotalElements());

        verify(productRepository, times(1)).findAll(any(PageRequest.class));
    }

    // TEST DELETE PRODUCT
    @Test
    void testDeleteProduct(){

        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

}