package com.focados.foca.shared.common.utils.exceptions;

public class UserNotAllowedException extends RuntimeException {
    public UserNotAllowedException() {
        super("Você não tem permissão para acessar este curso.");
    }

    public UserNotAllowedException(String message) {
        super(message);
    }
}
