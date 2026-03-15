package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.UserRequestDTO;
import com.ecommerce.project_backend.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    Page<UserResponseDTO> getAllUsers(int page, int size);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);
}
