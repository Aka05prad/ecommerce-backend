package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    PaymentServiceImpl paymentService = new PaymentServiceImpl();

    @Test
    void testProcessPayment() {

        PaymentStatus status = paymentService.processPayment(100);

        assertTrue(
                status == PaymentStatus.SUCCESS ||
                        status == PaymentStatus.FAILED
        );
    }
}
