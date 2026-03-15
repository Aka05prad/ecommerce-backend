package com.ecommerce.project_backend.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
