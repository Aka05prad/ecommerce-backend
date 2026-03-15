package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.Cart;

public interface CartService {

    Cart addToCart(Long userId, Long productId, int quantity);

    Cart getCart(Long userId);

    Cart removeItem(Long cartItemId);

    Cart updateQuantity(Long cartItemId, int quantity);
}
