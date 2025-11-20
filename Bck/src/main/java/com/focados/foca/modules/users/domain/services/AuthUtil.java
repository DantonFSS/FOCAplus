package com.focados.foca.modules.users.domain.services;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtil {

    public static UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        // Assumindo que o token JWT tem o sub = userId (UUID)
        String userIdStr = authentication.getName(); // normalmente subject (sub) do JWT
        return UUID.fromString(userIdStr);
    }
}