package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.ProductRequestDTO;
import com.ecommerce.project_backend.dto.ProductResponseDTO;
import com.ecommerce.project_backend.entity.Product;
import com.ecommerce.project_backend.exception.ProductNotFoundException;
import com.ecommerce.project_backend.repository.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
//    public ProductResponseDTO addProduct(ProductRequestDTO dto){
//
//        Product product = modelMapper.map(dto, Product.class);
//
//        Product savedProduct = productRepository.save(product);
//
//        return modelMapper.map(savedProduct, ProductResponseDTO.class);
//    }
    public ProductResponseDTO addProduct(ProductRequestDTO dto){

        log.info("Creating new product: {}", dto.getName());

        Product product = modelMapper.map(dto, Product.class);

        Product savedProduct = productRepository.save(product);

        log.info("Product created successfully with id {}", savedProduct.getId());

        return modelMapper.map(savedProduct, ProductResponseDTO.class);
    }

//
@Override

public Page<ProductResponseDTO> getAllProducts(Pageable pageable){

    log.info("Fetching products with page {} and size {}", pageable.getPageNumber(), pageable.getPageSize());

    Page<Product> productPage = productRepository.findAll(pageable);

    List<ProductResponseDTO> dtoList = productPage.getContent()
            .stream()
            .map(product -> modelMapper.map(product, ProductResponseDTO.class))
            .toList();

    log.info("Total products found: {}", productPage.getTotalElements());

    return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
}
    @Override
    public ProductResponseDTO getProductById(Long id){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto){

        log.info("Updating product with id {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id {}", id);
                    return new ProductNotFoundException("Product not found");
                });

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        Product updatedProduct = productRepository.save(product);

        log.info("Product updated successfully with id {}", id);

        return modelMapper.map(updatedProduct, ProductResponseDTO.class);
    }

    @Override
    public void deleteProduct(Long id){

        log.info("Deleting product with id {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id {}", id);
                    return new ProductNotFoundException("Product not found");
                });

        productRepository.delete(product);

        log.info("Product deleted successfully with id {}", id);
    }
}
