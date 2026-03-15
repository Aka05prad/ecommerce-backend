package com.ecommerce.project_backend.repository;

import com.ecommerce.project_backend.entity.Cart;
import com.ecommerce.project_backend.entity.CartItem;
import com.ecommerce.project_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    //Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
