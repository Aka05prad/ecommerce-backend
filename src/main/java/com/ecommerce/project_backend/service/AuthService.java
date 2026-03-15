package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.LoginRequestDTO;

public interface AuthService {

    String login(LoginRequestDTO loginRequestDTO);

}
