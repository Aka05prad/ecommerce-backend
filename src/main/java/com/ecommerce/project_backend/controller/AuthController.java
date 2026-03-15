package com.ecommerce.project_backend.controller;

import com.ecommerce.project_backend.dto.LoginRequestDTO;
import com.ecommerce.project_backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management System", description = "APIs for managing user's authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO){

        return authService.login(loginRequestDTO);
    }
}
