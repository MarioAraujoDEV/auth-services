package com.authservice.auth_service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.authservice.auth_service.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String REFRESH_TOKEN_HEADER = "Refresh-Token";  // Cambiar si es otro nombre

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractJwtToken(request);
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);

        if (token != null && !token.isEmpty()) {
            try {
                DecodedJWT decodedJWT = jwtUtil.verifyToken(token);

                if (decodedJWT.getExpiresAt().before(new Date())) {  // Si ha expirado el JWT
                    if (refreshToken != null && !refreshToken.isEmpty()) {
                        Long userId = Long.parseLong(jwtUtil.verifyToken(refreshToken).getSubject());
                        String newAccessToken = jwtUtil.generateAccessToken(userId);  // Generar nuevo JWT
                        response.setHeader("Authorization", "Bearer " + newAccessToken);  // Enviar el nuevo JWT al cliente
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extrae el JWT del encabezado
        }
        return null;
    }
}
