package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.UserRequestDTO;
import com.ecommerce.project_backend.entity.Role;
import com.ecommerce.project_backend.entity.User;
import com.ecommerce.project_backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess(){

        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Rahul");
        dto.setEmail("rahul@test.com");
        dto.setPassword("1234");
        dto.setRole(Role.CUSTOMER);

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(dto.getName());
        savedUser.setEmail(dto.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(Role.CUSTOMER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var response = userService.registerUser(dto);

        assertEquals("Rahul", response.getName());
        assertEquals("rahul@test.com", response.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }
}
