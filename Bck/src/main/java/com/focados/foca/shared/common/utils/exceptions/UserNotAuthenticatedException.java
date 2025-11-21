package com.focados.foca.shared.common.utils.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException() {
        super("Usuário não autenticado");
    }
}
