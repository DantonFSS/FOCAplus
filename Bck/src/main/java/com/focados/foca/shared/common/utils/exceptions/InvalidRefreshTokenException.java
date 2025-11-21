package com.focados.foca.shared.common.utils.exceptions;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
        super("Refresh token inválido ou expirado");
    }
}
