package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.PaymentStatus;

public interface PaymentService {

    PaymentStatus processPayment(double amount);

}