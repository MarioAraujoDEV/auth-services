package com.authservice.auth_service.service;

import com.authservice.auth_service.dto.LoginRequestDTO;
import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.exception.BadCredentialsException;
import com.authservice.auth_service.exception.DatabaseConnectionException;
import com.authservice.auth_service.exception.EmptyFieldException;
import com.authservice.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public String login(LoginRequestDTO request){

        if (isEmpty(request.getEmail())) {
            throw new EmptyFieldException("Email");
        }
        if (isEmpty(request.getPassword())) {
            throw new EmptyFieldException("Password");
        }
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(BadCredentialsException::new);

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException();
            }

            return tokenService.generateToken(user);
        }catch (DataAccessException e) {
            throw new DatabaseConnectionException();
        }
    }

    private boolean isEmpty(String field) {
        return field == null || field.trim().isEmpty();
    }
}
