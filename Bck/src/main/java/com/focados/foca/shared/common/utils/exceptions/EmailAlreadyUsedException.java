package com.focados.foca.shared.common.utils.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("O email '" + email + "' já está em uso.");
    }
}
