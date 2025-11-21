package com.focados.foca.shared.common.utils.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username já está em uso: " + username);
    }
}