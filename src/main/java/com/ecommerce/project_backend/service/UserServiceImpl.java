package com.ecommerce.project_backend.service;

import com.ecommerce.project_backend.dto.UserRequestDTO;
import com.ecommerce.project_backend.dto.UserResponseDTO;
import com.ecommerce.project_backend.entity.User;
import com.ecommerce.project_backend.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto) {

        log.info("Registering new user with email: {}", dto.getEmail());



        if(userRepository.existsByEmail(dto.getEmail())){
            log.warn("Email already exists: {}", dto.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        //  Encrypt password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with ID: {}", savedUser.getId());

// Send email notification
//        emailService.sendEmail(
//                savedUser.getEmail(),
//                "Registration Successful",
//                "Hello " + savedUser.getName() + ", your account has been successfully created in our Ecommerce platform."
//        );
        try {
            emailService.sendEmail(
                    savedUser.getEmail(),
                    "Registration Successful",
                    "Hello " + savedUser.getName() + ", your account has been successfully created."
            );
        } catch (Exception e) {
            log.error("Email sending failed: {}", e.getMessage());
        }

        return mapToResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        log.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new RuntimeException("User not found");
                });

        return mapToResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(int page, int size) {

        log.info("Fetching users page: {} size: {}", page, size);

        Page<User> users = userRepository.findAll(PageRequest.of(page, size));

        return users.map(this::mapToResponseDTO);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {

        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updatedUser = userRepository.save(user);

        log.info("User updated successfully with ID: {}", id);

        return mapToResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        log.info("Deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);

        log.info("User deleted successfully with ID: {}", id);
    }

    private UserResponseDTO mapToResponseDTO(User user){

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }
}