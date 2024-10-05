package com.auth.authorize.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final String SECRET_KEY = "kQ5gwwUn5QZpUG94UhdqftZzapdgyqdFCmXZFEAot3bYFTbm2XZ5PWNnZoDZrCRbpuauB99gKkrhBD99txFFfFRmKFraaHb4TxE6FffxzrxD6opfJEjpQKgq2hFJaTMh7RyXPb9mRnegtKaxLrkfcJ9MeU6uYHayL3oFGWTMmRHmjw8bdwJDSUaYtxFkAoZ9shUcGNq9M6TpXcJBw8PjUUy6qjqKSjdzrzPn42n9H7MXq244JaHounD7TXfouRtn";


    public String extractUsername(String token) {
        return null;
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }
}
