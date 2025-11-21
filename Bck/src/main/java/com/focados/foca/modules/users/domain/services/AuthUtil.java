package com.focados.foca.modules.users.domain.services;


import com.focados.foca.shared.common.utils.exceptions.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtil {

    public static UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }

        // Assumindo que o token JWT tem o sub = userId (UUID)
        String userIdStr = authentication.getName();
        return UUID.fromString(userIdStr);
    }
}