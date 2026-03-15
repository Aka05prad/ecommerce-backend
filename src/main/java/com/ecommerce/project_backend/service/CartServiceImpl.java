package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.Cart;
import com.ecommerce.project_backend.entity.CartItem;
import com.ecommerce.project_backend.entity.Product;
import com.ecommerce.project_backend.entity.User;
import com.ecommerce.project_backend.repository.CartRepository;
import com.ecommerce.project_backend.repository.CartItemRepository;
import com.ecommerce.project_backend.repository.ProductRepository;
import com.ecommerce.project_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart addToCart(Long userId, Long productId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setTotalPrice(0);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

//        CartItem item = new CartItem();
//        item.setCart(cart);
//        item.setProduct(product);
//        item.setQuantity(quantity);
//
//        cartItemRepository.save(item);
        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if(item == null){
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
        }else{
            item.setQuantity(item.getQuantity() + quantity);
        }

        cartItemRepository.save(item);

        double total = cart.getTotalPrice() + (product.getPrice() * quantity);
        cart.setTotalPrice(total);

        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public Cart removeItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Cart cart = item.getCart();

        double productTotal = item.getProduct().getPrice() * item.getQuantity();
        cart.setTotalPrice(cart.getTotalPrice() - productTotal);

        cartItemRepository.delete(item);

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(Long cartItemId, int quantity) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Cart cart = item.getCart();

        double oldTotal = item.getProduct().getPrice() * item.getQuantity();
        double newTotal = item.getProduct().getPrice() * quantity;

        cart.setTotalPrice(cart.getTotalPrice() - oldTotal + newTotal);

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return cartRepository.save(cart);
    }
}
