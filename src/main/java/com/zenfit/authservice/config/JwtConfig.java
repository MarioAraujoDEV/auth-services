package com.zenfit.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${secret.key}")
    private String secret_key;

    @Value("${access.token.expiration}")
    private long accessTokenExpiration;

    @Value("${refresh.token.expiration}")
    private long refreshTokenExpiration;

    public String getSecret_key() {
        return secret_key;
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}