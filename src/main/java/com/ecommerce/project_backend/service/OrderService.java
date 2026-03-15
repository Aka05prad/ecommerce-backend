package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponseDTO placeOrder(Long userId);

    Page<OrderResponseDTO> getOrdersByUser(Long userId, int page, int size);

}
