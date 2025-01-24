package com.zenfit.authservice.controller;

import com.zenfit.authservice.dto.ApiResponseDTO;
import com.zenfit.authservice.exception.TokenRefreshException;
import com.zenfit.authservice.security.JwtService;
import com.zenfit.authservice.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/auth")
public class TokenController {

    @Autowired
    private JwtService tokenService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponseDTO> refreshToken(HttpServletRequest request , HttpServletResponse response) {
        String refreshToken = cookieUtil.extractCookie(request, "refresh_token");
        if(refreshToken != null && !refreshToken.isEmpty()){
            String accessToken = tokenService.refreshAccessToken(refreshToken);
            cookieUtil.createCookie(response, "access_token", accessToken , 900);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO("Token refreshed successfully",null)); // 201 CREATED
        }else{
            cookieUtil.deleteCookie(response, "access_token");
            cookieUtil.deleteCookie(response, "refresh_token");
            throw new TokenRefreshException(); // 500
        }
    }
}
