package com.authservice.auth_service.service;

import com.authservice.auth_service.entity.User;
import com.authservice.auth_service.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TokenService {

    private final JwtUtil jwtUtil;

    @Autowired
    public TokenService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateToken(User user) {
        return jwtUtil.generateAccessToken(user.getId());
    }

}
