package com.ecommerce.project_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class ProductRequestDTO {
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    @Positive(message = "Stock must be positive")
    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, String description, double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}
