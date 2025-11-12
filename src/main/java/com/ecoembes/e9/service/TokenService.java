package com.ecoembes.e9.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class TokenService {

    private final InMemoryStore store;

    public TokenService(InMemoryStore store) {
        this.store = store;
    }

    public String createTokenFor(String email) {
        String token = Instant.now().toString() + "-" + UUID.randomUUID().toString();
        store.getActiveTokens().put(token, email);
        return token;
    }

    public void revokeToken(String token) {
        store.getActiveTokens().remove(token);
    }

    public boolean isValid(String token) {
        return token != null && store.getActiveTokens().containsKey(token);
    }

    public String getEmailForToken(String token) {
        return store.getActiveTokens().get(token);
    }
}
