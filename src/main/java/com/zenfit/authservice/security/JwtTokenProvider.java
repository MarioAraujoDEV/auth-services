package com.zenfit.authservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.zenfit.authservice.config.JwtConfig;
import com.zenfit.authservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    public String generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    public String generateToken(User user, Long tokenExpiration) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret_key());
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("name", user.getName())
                .withClaim("lastname", user.getLastname())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret_key());
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public Date getIssuedAtFromToken(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getIssuedAt();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public Date getExpiresAtFromToken(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getExpiresAt();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
