package com.focados.foca.shared.common.utils.exceptions;

public class ShareCodeInvalidException extends RuntimeException {
    public ShareCodeInvalidException() {
        super("Código de compartilhamento inválido.");
    }

    public ShareCodeInvalidException(String message) {
        super(message);
    }
}
