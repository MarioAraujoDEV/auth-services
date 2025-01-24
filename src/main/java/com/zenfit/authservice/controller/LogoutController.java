package com.zenfit.authservice.controller;

import com.zenfit.authservice.dto.ApiResponseDTO;
import com.zenfit.authservice.exception.TokenRefreshException;
import com.zenfit.authservice.security.JwtService;
import com.zenfit.authservice.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class LogoutController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieUtil.extractCookie(request, "refresh_token");
        if(refreshToken != null && !refreshToken.isEmpty()){
            jwtService.deleteByToken(refreshToken);

            cookieUtil.deleteCookie(response, "access_token");
            cookieUtil.deleteCookie(response, "refresh_token");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDTO("Logged out successfully",null)); // 200 OK
        }else{
            cookieUtil.deleteCookie(response, "access_token");
            cookieUtil.deleteCookie(response, "refresh_token");
            throw new TokenRefreshException(); // 500
        }

    }

}
