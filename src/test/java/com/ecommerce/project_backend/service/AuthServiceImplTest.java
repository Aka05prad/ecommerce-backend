package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.LoginRequestDTO;
import com.ecommerce.project_backend.entity.User;
import com.ecommerce.project_backend.repository.UserRepository;
import com.ecommerce.project_backend.security.JwtUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AuthServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock JwtUtil jwtUtil;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks AuthServiceImpl authService;

    @Test
    void testLoginSuccess() {

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("test@test.com");
        dto.setPassword("123");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encoded");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123","encoded")).thenReturn(true);
        when(jwtUtil.generateToken("test@test.com")).thenReturn("token123");

        String token = authService.login(dto);

        assertEquals("token123",token);
    }

    @Test
    void testInvalidPassword() {

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("test@test.com");
        dto.setPassword("wrong");

        User user = new User();
        user.setPassword("encoded");

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(),any())).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> authService.login(dto));
    }
}
