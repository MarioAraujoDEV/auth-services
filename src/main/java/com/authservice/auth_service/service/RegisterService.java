package com.authservice.auth_service.service;

import com.authservice.auth_service.dto.RegisterRequestDTO;
import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.exception.DatabaseConnectionException;
import com.authservice.auth_service.exception.EmailAlreadyExistsException;
import com.authservice.auth_service.exception.UsernameAlreadyExistsException;
import com.authservice.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDTO userDTO) {
        String email = userDTO.getEmail().trim();
        String username = userDTO.getUsername().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        User user = new User(
                email,
                username,
                userDTO.getName().trim(),
                userDTO.getLastname().trim(),
                passwordEncoder.encode(userDTO.getPassword())
        );

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new DatabaseConnectionException();
        }
    }
}
