package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.OrderItemDTO;
import com.ecommerce.project_backend.dto.OrderResponseDTO;
import com.ecommerce.project_backend.entity.*;
import com.ecommerce.project_backend.exception.OutOfStockException;
import com.ecommerce.project_backend.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Transactional
    @Override
    public OrderResponseDTO placeOrder(Long userId) {

        log.info("Placing order for userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());

        PaymentStatus paymentStatus = paymentService.processPayment(cart.getTotalPrice());

        order.setPaymentStatus(paymentStatus);

        if(paymentStatus == PaymentStatus.SUCCESS){
            order.setOrderStatus("PLACED");
        } else {
            order.setOrderStatus("CANCELLED");
        }

        order = orderRepository.save(order);

        for(CartItem item : cart.getItems()){

            Product product = item.getProduct();

            if(product.getStock() < item.getQuantity()){
                throw new OutOfStockException("Product out of stock");
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            if(paymentStatus == PaymentStatus.SUCCESS){
                product.setStock(product.getStock() - item.getQuantity());
                productRepository.save(product);
            }
        }

        cart.getItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);

        log.info("Order placed successfully with id {}", order.getId());

        return mapToDTO(order);
    }

    @Override
    public Page<OrderResponseDTO> getOrdersByUser(Long userId, int page, int size) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        List<OrderResponseDTO> dtoList =
                orders.stream().map(this::mapToDTO).collect(Collectors.toList());

        return new PageImpl<>(dtoList, PageRequest.of(page, size), dtoList.size());
    }

    private OrderResponseDTO mapToDTO(Order order){

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getId());
        dto.setUserName(order.getUser().getName());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setOrderStatus(order.getOrderStatus());

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        List<OrderItemDTO> itemDTOs =
                items.stream()
                        .map(i -> new OrderItemDTO(
                                i.getProduct().getName(),
                                i.getQuantity(),
                                i.getPrice()))
                        .collect(Collectors.toList());

        dto.setItems(itemDTOs);

        return dto;
    }
}