package com.zenfit.authservice.controller;

import com.zenfit.authservice.dto.ApiResponseDTO;
import com.zenfit.authservice.dto.RegisterRequestDTO;
import com.zenfit.authservice.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        registerService.register(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDTO("User registered successfully",null)); // 201 CREATED
    }
}

