package com.ecommerce.project_backend.controller;

import com.ecommerce.project_backend.entity.Cart;
import com.ecommerce.project_backend.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management System", description = "APIs for managing cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "Add product to cart")
    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long userId,
                          @RequestParam Long productId,
                          @RequestParam int quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }

    @Operation(summary = "Get cart details")
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {

        return cartService.getCart(userId);
    }

    @Operation(summary = "Remove item from cart")
    @DeleteMapping("/remove/{cartItemId}")
    public Cart removeItem(@PathVariable Long cartItemId) {

        return cartService.removeItem(cartItemId);
    }

    @Operation(summary = "Update quantity in cart")
    @PutMapping("/update/{cartItemId}")
    public Cart updateQuantity(@PathVariable Long cartItemId,
                               @RequestParam int quantity) {

        return cartService.updateQuantity(cartItemId, quantity);
    }
}
