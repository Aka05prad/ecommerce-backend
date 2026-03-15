package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.entity.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentStatus processPayment(double amount) {

        // simulate random payment success/failure
        boolean success = new Random().nextBoolean();

        if(success){
            return PaymentStatus.SUCCESS;
        }

        return PaymentStatus.FAILED;
    }
}