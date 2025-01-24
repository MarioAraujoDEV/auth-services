package com.zenfit.authservice.controller;

import com.zenfit.authservice.dto.ApiResponseDTO;
import com.zenfit.authservice.dto.LoginRequestDTO;
import com.zenfit.authservice.entity.User;
import com.zenfit.authservice.service.LoginService;
import com.zenfit.authservice.security.JwtService;
import com.zenfit.authservice.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        User user = loginService.login(loginRequestDTO);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        jwtService.createRefreshToken(user, refreshToken);

        cookieUtil.createCookie(response, "access_token", accessToken, 900000);
        cookieUtil.createCookie(response, "refresh_token", refreshToken, 604800);
        Map<String, String> personalData = Map.of(
                "name", user.getName(),
                "lastname", user.getLastname()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseDTO("Login successful",personalData)); // 200 OK
    }
}
