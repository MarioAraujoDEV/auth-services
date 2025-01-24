package com.zenfit.authservice.service;

import com.zenfit.authservice.dto.LoginRequestDTO;
import com.zenfit.authservice.entity.User;
import com.zenfit.authservice.exception.BadCredentialsException;
import com.zenfit.authservice.repository.UserRepository;
import com.zenfit.authservice.security.JwtService;
import com.zenfit.authservice.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(LoginRequestDTO loginRequestDTO) {
        User user =  userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(BadCredentialsException::new);

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException();
        }

        return user;
    }
}
