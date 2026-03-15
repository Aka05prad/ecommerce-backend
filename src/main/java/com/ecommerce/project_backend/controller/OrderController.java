package com.ecommerce.project_backend.controller;

import com.ecommerce.project_backend.dto.OrderResponseDTO;
import com.ecommerce.project_backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing Orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Checkout")
    @PostMapping("/checkout/{userId}")
    public OrderResponseDTO checkout(@PathVariable Long userId) {
        return orderService.placeOrder(userId);
    }

    @Operation(summary = "Get user's orders")
    @GetMapping("/user/{userId}")
    public Page<OrderResponseDTO> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return orderService.getOrdersByUser(userId, page, size);
    }
}
