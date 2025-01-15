package com.authservice.auth_service.controller;

import com.authservice.auth_service.dto.ApiResponseDTO;
import com.authservice.auth_service.dto.LoginRequestDTO;
import com.authservice.auth_service.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String token = loginService.login(loginRequestDTO);
        return ResponseEntity.ok(new ApiResponseDTO("Login successful", token));
    }
}
