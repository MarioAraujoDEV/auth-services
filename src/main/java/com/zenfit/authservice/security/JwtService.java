package com.zenfit.authservice.security;

import com.zenfit.authservice.entity.RefreshToken;
import com.zenfit.authservice.entity.User;
import com.zenfit.authservice.exception.DatabaseConnectionException;
import com.zenfit.authservice.exception.TokenGenerationException;
import com.zenfit.authservice.exception.TokenRefreshException;
import com.zenfit.authservice.exception.TokenExpiredException;
import com.zenfit.authservice.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Autowired
    private TokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String generateAccessToken(User user) {
        try {
            return jwtTokenProvider.generateAccessToken(user);
        } catch (Exception e) {
            throw new TokenGenerationException();
        }
    }

    public String generateRefreshToken(User user) {
        try {
            return jwtTokenProvider.generateRefreshToken(user);
        } catch (Exception e) {
            throw new TokenGenerationException();
        }
    }

    public void createRefreshToken(User user, String refreshToken) {
        Date issuedAt = extractIssuedAt(refreshToken);
        Date expiresAt = extractExpiresAt(refreshToken);
        if (issuedAt == null || expiresAt == null) {
            throw new TokenRefreshException();
        }
        try {
            RefreshToken token = new RefreshToken(refreshToken, user, expiresAt, issuedAt);
            refreshTokenRepository.save(token);
        } catch (DataAccessException e) {
            throw new DatabaseConnectionException();
        }
    }

    public String refreshAccessToken(String refreshToken) {
        Optional<RefreshToken> tokenOptional = refreshTokenRepository.findByToken(refreshToken);
        if (tokenOptional.isEmpty()) {
            throw new TokenRefreshException();
        }
        if (tokenOptional.get().getExpiryDate().before(new Date())) {
            throw new TokenExpiredException();
        }
        return generateAccessToken(tokenOptional.get().getUser());
    }

    @Transactional
    public void deleteAllUserRefreshTokens(String username) {
        try {
            refreshTokenRepository.deleteByUserUsername(username);
        } catch (DataAccessException e) {
            throw new DatabaseConnectionException();
        }
    }

    @Transactional
    public void deleteByToken(String token) {
        try {
            refreshTokenRepository.deleteByToken(token);
        } catch (DataAccessException e) {
            throw new DatabaseConnectionException();
        }
    }

    public String extractSubject(String refreshToken) {
        return jwtTokenProvider.getSubjectFromToken(refreshToken);
    }

    public Date extractIssuedAt(String refreshToken) {
        return jwtTokenProvider.getIssuedAtFromToken(refreshToken);
    }

    public Date extractExpiresAt(String refreshToken) {
        return jwtTokenProvider.getExpiresAtFromToken(refreshToken);
    }
}
