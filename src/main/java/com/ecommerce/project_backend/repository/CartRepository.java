package com.ecommerce.project_backend.repository;

import com.ecommerce.project_backend.entity.Cart;
import com.ecommerce.project_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

//    Optional<Cart> findByUserId(Long userId);
}
