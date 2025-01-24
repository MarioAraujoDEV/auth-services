package com.zenfit.authservice.service;


import com.zenfit.authservice.dto.RegisterRequestDTO;
import com.zenfit.authservice.entity.User;
import com.zenfit.authservice.exception.DatabaseConnectionException;
import com.zenfit.authservice.exception.EmailAlreadyExistsException;
import com.zenfit.authservice.exception.UsernameAlreadyExistsException;
import com.zenfit.authservice.repository.UserRepository;
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

    public void register(RegisterRequestDTO registerRequestDTO) {
        validateUserData(registerRequestDTO);

        User user = new User(
                registerRequestDTO.getEmail().trim(),
                registerRequestDTO.getUsername().trim(),
                registerRequestDTO.getName().trim(),
                registerRequestDTO.getLastname().trim(),
                passwordEncoder.encode(registerRequestDTO.getPassword())
        );

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new DatabaseConnectionException();
        }
    }

    private void validateUserData(RegisterRequestDTO registerRequestDTO) {
        String email = registerRequestDTO.getEmail().trim();
        String username = registerRequestDTO.getUsername().trim();

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailAlreadyExistsException();
        });

        userRepository.findByUsername(username).ifPresent(user -> {
            throw new UsernameAlreadyExistsException();
        });
    }
}
