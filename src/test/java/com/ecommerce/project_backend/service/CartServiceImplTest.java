package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.*;
import com.ecommerce.project_backend.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CartServiceImpl cartService;

    @Test
    void testAddToCart_NewCart() {
        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addToCart(1L,1L,2);

        assertNotNull(result);
        verify(cartRepository,times(2)).save(any(Cart.class));
    }

    @Test
    void testGetCart() {
        User user = new User();
        Cart cart = new Cart();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        Cart result = cartService.getCart(1L);

        assertEquals(cart,result);
    }

    @Test
    void testRemoveItem() {
        Product product = new Product();
        product.setPrice(100);

        Cart cart = new Cart();
        cart.setTotalPrice(200);

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(1);
        item.setCart(cart);

        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = cartService.removeItem(1L);

        assertEquals(100, result.getTotalPrice());
    }
}
