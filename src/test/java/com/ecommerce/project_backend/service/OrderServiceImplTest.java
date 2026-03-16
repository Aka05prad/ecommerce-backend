package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.*;
import com.ecommerce.project_backend.repository.*;
import com.ecommerce.project_backend.dto.OrderResponseDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class OrderServiceImplTest {

    @Mock CartRepository cartRepository;
    @Mock OrderRepository orderRepository;
    @Mock OrderItemRepository orderItemRepository;
    @Mock ProductRepository productRepository;
    @Mock UserRepository userRepository;
    @Mock PaymentService paymentService;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void testPlaceOrder_Success() {

        User user = new User();
        user.setId(1L);
        user.setName("Test");

        Product product = new Product();
        product.setPrice(100);
        product.setStock(10);

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(1);

        List<CartItem> items = new ArrayList<>();
        items.add(item);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(100);
        cart.setItems(items);
//        cart.setItems(List.of(item));
        //cart.setItems(new ArrayList<>(List.of(item)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(paymentService.processPayment(anyDouble())).thenReturn(PaymentStatus.SUCCESS);
        //when(orderRepository.save(any())).thenReturn(new Order());
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));


        OrderResponseDTO result = orderService.placeOrder(1L);

        assertNotNull(result);
        verify(orderRepository).save(any());
    }

    @Test
    void testGetOrdersByUser() {

        User user = new User();
        user.setName("User");

        Order order = new Order();
        order.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.findByUser(user)).thenReturn(List.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(new ArrayList<>());

        var result = orderService.getOrdersByUser(1L,0,5);

        assertEquals(1, result.getTotalElements());
    }
}
